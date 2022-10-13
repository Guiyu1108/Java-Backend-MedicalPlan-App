package com.advanced.bigdata.indexing.model;

/**
 * Model used for JWT Response
 */
public class JwtResponse {

    private String token;

    public JwtResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
