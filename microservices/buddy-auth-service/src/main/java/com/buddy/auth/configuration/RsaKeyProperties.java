package com.buddy.auth.configuration;

import com.nimbusds.jose.jwk.RSAKey;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.InputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

/**
 * Configuration properties to hold RSA public and private keys for JWT signing and verification.
 * This class now supports loading keys from a Java KeyStore (JKS) file, which is
 * recommended for production environments for better security and key management.
 */
@Configuration
@ConfigurationProperties(prefix = "jwt.key")
public class RsaKeyProperties {

    private final ResourceLoader resourceLoader;

    private String keyStore;
    private char[] keyStorePassword;
    private String keyAlias;
    private char[] keyPassword;
    private String keyStoreType = "JKS"; // Default to JKS, can be overridden by application.yml

    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;
    

    public RsaKeyProperties(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
    
    /**
     * Initializes the public and private keys by loading them from the configured KeyStore.
     * This method runs after the properties have been set by Spring.
     * @throws Exception if there's an error loading keys from the keystore.
     */
    @PostConstruct
    public void loadKeysFromKeystore() throws Exception {
        Resource resource = resourceLoader.getResource(keyStore);

        try (InputStream inputStream = resource.getInputStream()) {
            KeyStore ks = KeyStore.getInstance(keyStoreType);
            ks.load(inputStream, keyStorePassword);

            Key key = ks.getKey(keyAlias, keyPassword);
            this.privateKey = (RSAPrivateKey) key;

            Certificate cert = ks.getCertificate(keyAlias);
            this.publicKey = (RSAPublicKey) cert.getPublicKey();
        }
    }

    public RSAPublicKey getPublicKey() {
        if (publicKey == null) {
            throw new IllegalStateException("RSA Public Key has not been loaded. Check KeyStore configuration.");
        }
        return publicKey;
    }

    public RSAPrivateKey getPrivateKey() {
        if (privateKey == null) {
            throw new IllegalStateException("RSA Private Key has not been loaded. Check KeyStore configuration.");
        }
        return privateKey;
    }

    // Setters for properties from application.yml
    public String getKeyStore() { return keyStore; }
    public void setKeyStore(String keyStore) { this.keyStore = keyStore; }
    public char[] getKeyStorePassword() { return keyStorePassword; }
    public void setKeyStorePassword(char[] keyStorePassword) { this.keyStorePassword = keyStorePassword; }
    public String getKeyAlias() { return keyAlias; }
    public void setKeyAlias(String keyAlias) { this.keyAlias = keyAlias; }
    public char[] getKeyPassword() { return keyPassword; }
    public void setKeyPassword(char[] keyPassword) { this.keyPassword = keyPassword; }
    public String getKeyStoreType() { return keyStoreType; }
    public void setKeyStoreType(String keyStoreType) { this.keyStoreType = keyStoreType; } // Setter for keyStoreType

    /**
     * Creates an RSAKey object from the public and private keys.
     * This is used by the `NimbusJwtEncoder` for JWT signing.
     * @return RSAKey instance.
     */
    public RSAKey toRSAKey() {
        return new RSAKey.Builder(getPublicKey())
                .privateKey(getPrivateKey())
                .keyID(UUID.randomUUID().toString())
                .build();
    }
}