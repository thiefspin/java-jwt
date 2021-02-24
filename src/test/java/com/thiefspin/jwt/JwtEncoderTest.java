package com.thiefspin.jwt;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class JwtEncoderTest {

    @Test
    @DisplayName("Encoding a header should work")
    void testEncode() {
        final var expectedHeader = "eyJhbGciOiJIUzI1NiIsImV4dHJhSGVhZGVyIjpudWxsLCJ0eXBlIjoiSldUIn0";
        JwtEncoder.encodeHeader(Algorithm.HS256, Optional.empty())
            .ifPresentOrElse(
                header -> assertEquals(expectedHeader, header),
                () -> fail("Failed to encode Jwt header")
            );
    }

    @Test
    @DisplayName("Creating a JwtHeader as JSON should work")
    void testHeaderCreation() {
        final var headerJson = JwtEncoder.getHeaderJson(Algorithm.HS256, Optional.empty());
        if (headerJson.isPresent()) {
            assertEquals(headerJson.get(), "{\"alg\":\"HS256\",\"extraHeader\":null,\"type\":\"JWT\"}");
        } else {
            fail("Could not parse JwtHeader as JSON");
        }
    }

    @Test
    @DisplayName("Encoding a string to base 64 should produce the correct result")
    void testEncodeBase64() {
        final var str = "Some or other string";
        assertEquals("U29tZSBvciBvdGhlciBzdHJpbmc", JwtEncoder.encodeBase64(str));
    }
}
