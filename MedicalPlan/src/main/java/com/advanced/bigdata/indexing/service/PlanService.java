package com.advanced.bigdata.indexing.service;


import com.google.common.annotations.VisibleForTesting;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import java.util.*;

/**
 * PlanService which supports POST, PUT, PATCH, GET and DELETE Http Methods for
 * User operation.
 */
@Service
public class PlanService {
    private final Jedis jedis;
    private final ETagService eTagService;

    public PlanService(Jedis jedis, ETagService eTagService) {
        this.jedis = jedis;
        this.eTagService = eTagService;
    }

    public boolean isKeyPresent(String key) {
        Map<String, String> value = jedis.hgetAll(key);
        jedis.close();
        return !(value == null || value.isEmpty());
    }

    /**
     * This generates a RSA-signed JWT token used to authenticate future requests.
     * @param key
     * @return RSA-signed JWT token
     */
    public String getETag(String key) {
        return jedis.hget(key, "eTag");
    }

    /**
     *
     * @param key
     * @param jsonObject
     * @return
     */
    public String setETag(String key, JSONObject jsonObject) {
        String eTag = eTagService.getETag(jsonObject);
        jedis.hset(key, "eTag", eTag);
        return eTag;
    }

    /**
     * Creates a new plan provided in the request body
     * @param plan  plan provided in the request body
     * @param key JWT token used to authenticate
     * @return eTag
     */
    public String createPlan(JSONObject plan, String key) {
        jsonToMap(plan);
        return setETag(key, plan);
    }

    /**
     * Fetches an existing plan provided by the id
     * @param key PlanId
     * @return Plan based on the key
     */
    public Map<String, Object> getPlan(String key) {
        Map<String, Object> result = new HashMap<>();
        getOrDelete(key, result, false);
        return result;
    }

    /**
     * Deletes an existing plan provided by the id
     * @param key Plan Id
     */
    public void deletePlan(String key) {
        getOrDelete(key, null, true);
    }

    @VisibleForTesting
    protected Map<String, Map<String, Object>> jsonToMap(JSONObject jsonObject) {
        Map<String, Map<String, Object>> map = new HashMap<>();
        Map<String, Object> contentMap = new HashMap<>();

        for (String key : jsonObject.keySet()) {
            String redisKey = jsonObject.get("objectType") + ":" + jsonObject.get("objectId");
            Object value = jsonObject.get(key);

            if (value instanceof JSONObject) {
                value = jsonToMap((JSONObject) value);
                jedis.sadd(redisKey + ":" + key, ((Map<String, Map<String, Object>>) value).entrySet().iterator().next().getKey());
            } else if (value instanceof JSONArray) {
                value = jsonToList((JSONArray) value);
                ((List<Map<String, Map<String, Object>>>) value)
                        .forEach((entry) -> {
                            entry.keySet()
                                    .forEach((listKey) -> {
                                        jedis.sadd(redisKey + ":" + key, listKey);
                                    });
                        });
            } else {
                jedis.hset(redisKey, key, value.toString());
                contentMap.put(key, value);
                map.put(redisKey, contentMap);
            }
        }
        return map;
    }


    @VisibleForTesting
    protected Map<String, Object> getOrDelete(String redisKey, Map<String, Object> resultMap, boolean isDelete) {
        Set<String> keys = jedis.keys(redisKey + ":*");
        keys.add(redisKey);

        for (String key : keys) {
            if (key.equals(redisKey)) {
                if (isDelete) jedis.del(new String[]{key});
                else {
                    Map<String, String> object = jedis.hgetAll(key);
                    for (String attrKey : object.keySet()) {
                        if (!attrKey.equalsIgnoreCase("eTag")) {
                            resultMap.put(attrKey, isInteger(object.get(attrKey)) ? Integer.parseInt(object.get(attrKey)) : object.get(attrKey));
                        }
                    }
                }
            } else {
                String newKey = key.substring((redisKey + ":").length());
                Set<String> members = jedis.smembers(key);
                if (members.size() > 1 || newKey.equals("linkedPlanServices")) {
                    List<Object> listObj = new ArrayList<>();
                    for (String member : members) {
                        if (isDelete) {
                            getOrDelete(member, null, true);
                        } else {
                            Map<String, Object> listMap = new HashMap<>();
                            listObj.add(getOrDelete(member, listMap, false));
                        }
                    }
                    if (isDelete) jedis.del(new String[]{key});
                    else resultMap.put(newKey, listObj);
                } else {
                    if (isDelete) {
                        jedis.del(new String[]{members.iterator().next(), key});
                    } else {
                        Map<String, String> object = jedis.hgetAll(members.iterator().next());
                        Map<String, Object> nestedMap = new HashMap<>();
                        for (String attrKey : object.keySet()) {
                            nestedMap.put(attrKey,
                                    isInteger(object.get(attrKey)) ? Integer.parseInt(object.get(attrKey)) : object.get(attrKey));
                        }
                        resultMap.put(newKey, nestedMap);
                    }
                }
            }
        }
        return resultMap;
    }

    @VisibleForTesting
    protected List<Object> jsonToList(JSONArray jsonArray) {
        List<Object> result = new ArrayList<>();
        for (Object value : jsonArray) {
            if (value instanceof JSONArray) value = jsonToList((JSONArray) value);
            else if (value instanceof JSONObject) value = jsonToMap((JSONObject) value);
            result.add(value);
        }
        return result;
    }

    private boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
