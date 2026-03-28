package com.buddy.auth.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.buddy.auth.configuration.RsaKeyProperties;
import com.nimbusds.jose.jwk.JWKSet;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class JwkController {

    private final RsaKeyProperties rsaKeyProperties;

    @GetMapping("/.well-known/jwks.json")
    public Map<String, Object> keys() {
        // This safely exposes ONLY the public key components
        return new JWKSet(rsaKeyProperties.toRSAKey()).toJSONObject();
    }
}