package com.thiefspin.jwt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class JwtHeaderTest {

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
        assertSame(Algorithm.HS256, header.getAlg());
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

    @Test
    @DisplayName("should deserialize from json")
    void testJsonDeserialization() throws JwtException {
        final var json = "{\"alg\":\"HS256\",\"extraHeader\":null,\"type\":\"JWT\"}";
        final var header = JwtHeader.fromJson(json);
        assertNull(header.getExtraHeader());
        assertEquals(Algorithm.HS256, header.getAlg());
        assertEquals("JWT", header.getType());
    }
}
