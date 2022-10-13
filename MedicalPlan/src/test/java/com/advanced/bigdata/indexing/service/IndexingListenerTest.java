package com.advanced.bigdata.indexing.service;

import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

class IndexingListenerTest {
    @Mock
    RestHighLevelClient client;
    @Mock
    LinkedHashMap<String, Map<String, Object>> MapOfDocuments;
    @Mock
    ArrayList<String> listOfKeys;
    @InjectMocks
    IndexingListener indexingListener;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testReceiveMessage() throws IOException {
        indexingListener.receiveMessage(Map.of("String", "String"));
    }

    @Test
    void testIndexExists() throws IOException {
        boolean result = indexingListener.indexExists();
        Assertions.assertEquals(true, result);
    }

    @Test
    void testPostDocument() throws IOException {
        indexingListener.postDocument(null);
    }

    @Test
    void testDeleteDocument() throws IOException {
        indexingListener.deleteDocument(null);
    }

    @Test
    void testConvertToKeys() {
        Map<String, Map<String, Object>> result = indexingListener.convertToKeys(null);
        Assertions.assertEquals(Map.of("String", Map.of("String", "Map")), result);
    }

    @Test
    void testConvertToKeysList() {
        List<Object> result = indexingListener.convertToKeysList(null);
        Assertions.assertEquals(List.of("replaceMeWithExpectedResult"), result);
    }

    @Test
    void testConvertMapToDocumentIndex() {
        Map<String, Map<String, Object>> result = indexingListener.convertMapToDocumentIndex(null, "parentId", "objectName");
        Assertions.assertEquals(Map.of("String", Map.of("String", "Map")), result);
    }

    @Test
    void testConvertToList() {
        List<Object> result = indexingListener.convertToList(null, "parentId", "objectName");
        Assertions.assertEquals(List.of("replaceMeWithExpectedResult"), result);
    }

    @Test
    void testCreateElasticIndex() throws IOException {
        indexingListener.createElasticIndex();
    }
}
