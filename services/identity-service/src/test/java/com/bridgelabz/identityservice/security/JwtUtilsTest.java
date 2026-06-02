package com.bridgelabz.identityservice.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

public class JwtUtilsTest {

    private JwtUtils jwtUtils;

    @BeforeEach
    public void setup() {
        jwtUtils = new JwtUtils();
        // Set properties that are normally injected via @Value
        ReflectionTestUtils.setField(jwtUtils, "jwtSecret", "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970");
        ReflectionTestUtils.setField(jwtUtils, "jwtExpirationMs", 3600000); // 1 hour
    }

    @Test
    public void testGenerateAndValidateJwtToken() {
        String token = jwtUtils.generateJwtToken("test@example.com");
        
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(jwtUtils.validateJwtToken(token), "Generated token should be valid");
    }

    @Test
    public void testGetEmailFromJwtToken() {
        String email = "test@example.com";
        String token = jwtUtils.generateJwtToken(email);
        
        assertEquals(email, jwtUtils.getEmailFromJwtToken(token));
    }

    @Test
    public void testInvalidJwtToken() {
        assertFalse(jwtUtils.validateJwtToken("this.is.an.invalid.token"));
    }

    @Test
    public void testSha256Hashing() {
        String input = "my_secret_token";
        
        String hash1 = jwtUtils.sha256(input);
        String hash2 = jwtUtils.sha256(input);
        
        assertNotNull(hash1);
        assertEquals(64, hash1.length(), "SHA-256 hex string should be exactly 64 characters long");
        assertEquals(hash1, hash2, "Hashing the same input should yield the exact same output");
        assertNotEquals(input, hash1, "Hash output should not match the plain text input");
    }
}
