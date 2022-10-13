package com.advanced.bigdata.indexing.filter;

import com.advanced.bigdata.indexing.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Set;

import static org.mockito.Mockito.*;

class JwtFilterTest {
    @Mock
    ObjectMapper mapper;
    @Mock
    JwtUtil jwtUtil;
    @Mock
    Log logger;
    @Mock
    Environment environment;
    @Mock
    ServletContext servletContext;
    @Mock
    FilterConfig filterConfig;
    @Mock
    Set<String> requiredProperties;
    @InjectMocks
    JwtFilter jwtFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testShouldNotFilter() {
        boolean result = jwtFilter.shouldNotFilter(null);
        Assertions.assertEquals(true, result);
    }

    @Test
    void testDoFilterInternal() throws ServletException, IOException {
        when(jwtUtil.validateToken(anyString())).thenReturn(Boolean.TRUE);
        jwtFilter.doFilterInternal(null, null, null);
    }
}