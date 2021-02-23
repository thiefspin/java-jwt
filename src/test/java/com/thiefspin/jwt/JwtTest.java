package com.thiefspin.jwt;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

public class JwtTest {

    private final String secret = "very-secret";

    @Test
    @DisplayName("Encoding a header should work")
    void testEncode() {
        final var expectedHeader = "eyJhbGciOiJIUzI1NiIsImV4dHJhSGVhZGVyIjpudWxsLCJ0eXBlIjoiSldUIn0";
        Jwt.encodeHeader(Algorithm.HS256, Optional.empty())
            .ifPresentOrElse(
                header -> assertEquals(expectedHeader, header),
                () -> fail("Failed to encode Jwt header")
            );
    }

    @Test
    @DisplayName("Creating a JwtHeader as JSON should work")
    void testHeaderCreation() {
        final var headerJson = Jwt.getHeaderJson(Algorithm.HS256, Optional.empty());
        if (headerJson.isPresent()) {
            assertEquals(headerJson.get(), "{\"alg\":\"HS256\",\"extraHeader\":null,\"type\":\"JWT\"}");
        } else {
            fail("Could not parse JwtHeader as JSON");
        }
    }

    @Test
    @DisplayName("Encode token with the minimal API")
    void testEncodeSimple() {
        final var payload = "This is a payload";
        final var encoded = Jwt.encode(secret, payload);
        final var expectedHeaderPart = "eyJhbGciOiJIUzI1NiIsImV4dHJhSGVhZGVyIjpudWxsLCJ0eXBlIjoiSldUIn0";
        final var expectedPayloadPart = "VGhpcyBpcyBhIHBheWxvYWQ";

        testEncodedString(encoded, expectedHeaderPart, expectedPayloadPart);
    }

    @Test
    @DisplayName("Encode token with all parameters")
    void testEncodeWithAllParams() {
        final var payload = "This is a payload";
        final var header = Optional.of("Content-type: application/json");
        final var encoded = Jwt.encode(secret, payload, header, Optional.of(Algorithm.HS256));
        final var expectedHeaderPart = "eyJhbGciOiJIUzI1NiIsImV4dHJhSGVhZGVyIjpDb250ZW50LXR5cGU6IGFwcGxpY2F0aW9uL2pzb24sInR5cGUiOiJKV1QifQ";
        final var expectedPayloadPart = "VGhpcyBpcyBhIHBheWxvYWQ";

        testEncodedString(encoded, expectedHeaderPart, expectedPayloadPart);
    }

    @Test
    @DisplayName("Encode token without header")
    void testEncodeWithoutHeader() {
        final var payload = "This is a payload";
        final var encoded = Jwt.encode(secret, payload, Algorithm.HS256);
        final var expectedHeaderPart = "eyJhbGciOiJIUzI1NiIsImV4dHJhSGVhZGVyIjpudWxsLCJ0eXBlIjoiSldUIn0";
        final var expectedPayloadPart = "VGhpcyBpcyBhIHBheWxvYWQ";

        testEncodedString(encoded, expectedHeaderPart, expectedPayloadPart);
    }

    @Test
    @DisplayName("Encode token without algorithm")
    void testEncodeWithoutAlgorithm() {
        final var payload = "This is a payload";
        final var header = "Content-type: application/json";
        final var encoded = Jwt.encode(secret, payload, header);
        final var expectedHeaderPart = "eyJhbGciOiJIUzI1NiIsImV4dHJhSGVhZGVyIjpDb250ZW50LXR5cGU6IGFwcGxpY2F0aW9uL2pzb24sInR5cGUiOiJKV1QifQ";
        final var expectedPayloadPart = "VGhpcyBpcyBhIHBheWxvYWQ";

        testEncodedString(encoded, expectedHeaderPart, expectedPayloadPart);
    }

    private void testEncodedString(JwtResult encoded, String expectedHeaderPart, String expectedPayloadPart) {
        if (encoded.getToken().isEmpty() || encoded.getException().isPresent()) {
            fail("Failed to encode token");
        }

        encoded.getToken().map(token -> {
            assertEquals(expectedHeaderPart, token.split("\\.")[0]);
            assertEquals(expectedPayloadPart, token.split("\\.")[1]);
            return token;
        });
    }
}
