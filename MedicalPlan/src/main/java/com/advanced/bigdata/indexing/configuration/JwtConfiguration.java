package com.advanced.bigdata.indexing.configuration;

import com.advanced.bigdata.indexing.model.JwtKeys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.*;

@Configuration
public class JwtConfiguration {

    @Bean
    public JwtKeys getKeys() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        return new JwtKeys(publicKey, privateKey);
    }
}
