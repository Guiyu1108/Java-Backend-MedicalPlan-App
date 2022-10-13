package com.advanced.bigdata.indexing.util;

import com.advanced.bigdata.indexing.model.JwtKeys;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.mockito.Mockito.*;

class JwtUtilTest {
    @Mock
    JwtKeys jwtKeys;
    @InjectMocks
    JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerateToken() {
        when(jwtKeys.getPrivateKey()).thenReturn(null);

        String result = jwtUtil.generateToken();
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testExtractExpiration() {
        when(jwtKeys.getPublicKey()).thenReturn(null);

        Date result = jwtUtil.extractExpiration("token");
        Assertions.assertEquals(new GregorianCalendar(2022, Calendar.OCTOBER, 12, 20, 3).getTime(), result);
    }

    @Test
    void testExtractClaim() {
        when(jwtKeys.getPublicKey()).thenReturn(null);

        String result = jwtUtil.extractClaim("token", null);
        Assertions.assertEquals("token", result);
    }

    @Test
    void testValidateToken() {
        when(jwtKeys.getPublicKey()).thenReturn(null);

        Boolean result = jwtUtil.validateToken("token");
        Assertions.assertEquals(Boolean.TRUE, result);
    }
}