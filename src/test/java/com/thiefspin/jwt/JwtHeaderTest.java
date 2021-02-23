package com.thiefspin.jwt;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class JwtHeaderTest {

    @Test
    @DisplayName("should create a JwtHeader successfully")
    void testJwtHeader() {
        final var header = new JwtHeader(Algorithm.HS256);
        assertNull(header.getExtraHeader());
        assertSame(header.getAlg(), Algorithm.HS256);
    }

    @Test
    @DisplayName("should create a JwtHeader successfully with an extra header")
    void testCustomJwtHeader() {
        final var extraHeader = "Content-type: application/json";
        final var header = new JwtHeader(Algorithm.HS256, extraHeader);
        assertSame(extraHeader, header.getExtraHeader());
        assertSame(header.getAlg(), Algorithm.HS256);
    }

    @Test
    @DisplayName("should create JSON string with class fields")
    void testJwtHeaderJsonSerialization() {
        final var header = new JwtHeader(Algorithm.HS256);
        final Optional<String> json = header.toJson();
        if (json.isPresent()) {
            assertEquals("{\"alg\":\"HS256\",\"extraHeader\":null,\"type\":\"JWT\"}", json.get());
        } else {
            fail("JwtHeader could not be parsed as JSON");
        }
    }
}
