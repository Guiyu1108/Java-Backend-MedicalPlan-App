package com.advanced.bigdata.indexing.util;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;

/**
 * Util class to help service like IndexingListener to get the mapping
 */
public class IndexingHelper {
    public static XContentBuilder getMapping() throws IOException {

        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.startObject("properties");
            {
                builder.startObject("plan");
                {
                    builder.startObject("properties");
                    {
                        builder.startObject("_org");
                        {
                            builder.field("type", "text");
                        }
                        builder.endObject();
                        builder.startObject("objectId");
                        {
                            builder.field("type", "keyword");
                        }
                        builder.endObject();
                        builder.startObject("objectType");
                        {
                            builder.field("type", "text");
                        }
                        builder.endObject();
                        builder.startObject("planType");
                        {
                            builder.field("type", "text");
                        }
                        builder.endObject();
                        builder.startObject("creationDate");
                        {
                            builder.field("type", "date");
                            builder.field("format", "MM-dd-yyyy");
                        }
                        builder.endObject();
                    }
                    builder.endObject();
                }
                builder.endObject();
                builder.startObject("planCostShares");
                {
                    builder.startObject("properties");
                    {
                        builder.startObject("copay");
                        {
                            builder.field("type", "long");
                        }
                        builder.endObject();
                        builder.startObject("deductible");
                        {
                            builder.field("type", "long");
                        }
                        builder.endObject();
                        builder.startObject("_org");
                        {
                            builder.field("type", "text");
                        }
                        builder.endObject();
                        builder.startObject("objectId");
                        {
                            builder.field("type", "keyword");
                        }
                        builder.endObject();
                        builder.startObject("objectType");
                        {
                            builder.field("type", "text");
                        }
                        builder.endObject();
                    }
                    builder.endObject();
                }
                builder.endObject();
                builder.startObject("linkedPlanServices");
                {
                    builder.startObject("properties");
                    {
                        builder.startObject("_org");
                        {
                            builder.field("type", "text");
                        }
                        builder.endObject();
                        builder.startObject("objectId");
                        {
                            builder.field("type", "keyword");
                        }
                        builder.endObject();
                        builder.startObject("objectType");
                        {
                            builder.field("type", "text");
                        }
                        builder.endObject();
                    }
                    builder.endObject();
                }
                builder.endObject();
                builder.startObject("linkedService");
                {
                    builder.startObject("properties");
                    {
                        builder.startObject("_org");
                        {
                            builder.field("type", "text");
                        }
                        builder.endObject();
                        builder.startObject("name");
                        {
                            builder.field("type", "text");
                        }
                        builder.endObject();
                        builder.startObject("objectId");
                        {
                            builder.field("type", "keyword");
                        }
                        builder.endObject();
                        builder.startObject("objectType");
                        {
                            builder.field("type", "text");
                        }
                        builder.endObject();
                    }
                    builder.endObject();
                }
                builder.endObject();
                builder.startObject("planserviceCostShares");
                {
                    builder.startObject("properties");
                    {
                        builder.startObject("copay");
                        {
                            builder.field("type", "long");
                        }
                        builder.endObject();
                        builder.startObject("deductible");
                        {
                            builder.field("type", "long");
                        }
                        builder.endObject();
                        builder.startObject("_org");
                        {
                            builder.field("type", "text");
                        }
                        builder.endObject();
                        builder.startObject("objectId");
                        {
                            builder.field("type", "keyword");
                        }
                        builder.endObject();
                        builder.startObject("objectType");
                        {
                            builder.field("type", "text");
                        }
                        builder.endObject();
                    }
                    builder.endObject();
                }
                builder.endObject();
                builder.startObject("plan_join");
                {
                    builder.field("type", "join");
                    builder.field("eager_global_ordinals", "true");
                    builder.startObject("relations");
                    {
                        builder.array("plan", "planCostShares", "linkedPlanServices");
                        builder.array("linkedPlanServices", "linkedService", "planserviceCostShares");
                    }
                    builder.endObject();
                }
                builder.endObject();
            }
            builder.endObject();
        }
        builder.endObject();

        return builder;
    }
}
