package com.advanced.bigdata.indexing.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

class PlanServiceTest {
    @Mock
    Jedis jedis;
    @Mock
    ETagService eTagService;
    @InjectMocks
    PlanService planService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIsKeyPresent() {
        boolean result = planService.isKeyPresent("key");
        Assertions.assertEquals(true, result);
    }

    @Test
    void testGetETag() {
        String result = planService.getETag("key");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testSetETag() {
        when(eTagService.getETag(any())).thenReturn("getETagResponse");

        String result = planService.setETag("key", null);
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testCreatePlan() {
        when(eTagService.getETag(any())).thenReturn("getETagResponse");

        String result = planService.createPlan(null, "key");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetPlan() {
        Map<String, Object> result = planService.getPlan("key");
        Assertions.assertEquals(Map.of("String", "replaceMeWithExpectedResult"), result);
    }

    @Test
    void testDeletePlan() {
        planService.deletePlan("key");
    }

    @Test
    void testJsonToMap() {
        Map<String, Map<String, Object>> result = planService.jsonToMap(null);
        Assertions.assertEquals(Map.of("String", Map.of("String", "Map")), result);
    }

    @Test
    void testJsonToList() {
        List<Object> result = planService.jsonToList(null);
        Assertions.assertEquals(List.of("replaceMeWithExpectedResult"), result);
    }
}
