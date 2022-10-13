package com.advanced.bigdata.indexing.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class ETagServiceTest {
    ETagService eTagService = new ETagService();

    @Test
    void testGetETag() {
        String result = eTagService.getETag(null);
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testVerifyETag() {
        boolean result = eTagService.verifyETag(null, List.of("String"));
        Assertions.assertEquals(true, result);
    }
}