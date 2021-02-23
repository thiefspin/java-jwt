package com.thiefspin.jwt;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JwtEncoderTest {

    @Test
    @DisplayName("Encoding a string to base 64 should produce the correct result")
    void testEncodeBase64() {
        final var str = "Some or other string";
        assertEquals("U29tZSBvciBvdGhlciBzdHJpbmc", JwtEncoder.encodeBase64(str));
    }

    @Test
    @DisplayName("Sign a HMAC correctly")
    void testSignHmac() throws JwtException {
        final var payload = "This is a payload";
        final var key = "token";
        final var expectedHmac = "77-977-9EAPvv70HQ--_vWbvv73vv73vv73vv706cEHvv73htJxQdyl877-977-977-9Xe-_ve-_vQnvv70";
        assertEquals(expectedHmac, JwtEncoder.signHmac(payload, key, Algorithm.HS256));
    }
}
