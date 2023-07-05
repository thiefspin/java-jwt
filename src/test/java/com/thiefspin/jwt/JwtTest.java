package com.thiefspin.jwt;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class JwtTest {

    private final String secret = "very-secret";

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

    @Test
    @DisplayName("Decode a encoded token correctly")
    void testDecodeValidToken() {
        final var payload = "This is a payload";
        final var encoded = Jwt.encode(secret, payload);
        if (encoded.getToken().isPresent()) {
            final var decoded = Jwt.decode(encoded.getToken().get(), secret);
            if (decoded.getPayload().isPresent()) {
                assertEquals(decoded.getPayload().get(), payload);
            } else {
                fail("Failed to decode token");
            }
        } else {
            fail("Token encoding failed");
        }
    }

    @Test
    @DisplayName("Handle decoding an incorrect header token correctly")
    void testDecodeInvalidTokenHeader() {
        final var invalidToken = "firstpart.second.sjkncn";
        final var decoded = Jwt.decode(invalidToken, secret);
        if (decoded.getException().isPresent()) {
            assertEquals(
                JwtExceptionType.FAILED_TO_PARSE_HEADER.getMessage(),
                decoded.getException().get().getMessage()
            );
        }
    }

    private void testEncodedString(JwtEncodeResult encoded, String expectedHeaderPart, String expectedPayloadPart) {
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
