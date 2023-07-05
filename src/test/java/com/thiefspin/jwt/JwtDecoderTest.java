package com.thiefspin.jwt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtDecoderTest {

    private final static String KEY = "very-secret";

    @Test
    @DisplayName("Decode a string from base64")
    void testDecodeBase64() {
        final var expectedString = "Some string";
        final var str = "U29tZSBzdHJpbmc";
        assertEquals(expectedString, JwtDecoder.decodeBase64(str));
    }

    @Test
    @DisplayName("Partition a token correctly")
    void testTokenPartition() throws JwtException {
        final var headerPart = "eyJhbGciOiJIUzI1NiIsImV4dHJhSGVhZGVyIjpudWxsLCJ0eXBlIjoiSldUIn0";
        final var payloadPart = "VGhpcyBpcyBhIHBheWxvYWQ";
        final var signature = "MO-_ve-_ve-_vV8lKxloyI9S77-9YTzvv71oHU87b--_ve-_vXjvv73vv71d77-977-977-977-9Cg";
        final var token = String.format("%s.%s.%s", headerPart, payloadPart, signature);
        final var parts = JwtDecoder.partitionToken(token);
        assertEquals(3, parts.length);
        assertEquals(headerPart, parts[0]);
        assertEquals(payloadPart, parts[1]);

    }

    @Test
    @DisplayName("Decode a valid token correctly")
    void testTokenDecode() {
        final var expectedPayload = "Some payload";
        System.out.println(Jwt.encode(KEY, expectedPayload).getToken());
        final var headerPart = "eyJhbGciOiJIUzI1NiIsImV4dHJhSGVhZGVyIjpudWxsLCJ0eXBlIjoiSldUIn0";
        final var payloadPart = "U29tZSBwYXlsb2Fk";
        final var signature = "fAhIeBLvv73vv73vv71UWWsZb1wx77-9Eyzvv71k77-9F--_ve-_vVTvv71iGkvvv71rHQ";
        final var token = String.format("%s.%s.%s", headerPart, payloadPart, signature);
        final var result = JwtDecoder.decodeToken(token, KEY);

        assertTrue(result.getException().isEmpty());
        if (result.getPayload().isEmpty()) fail("Payload should be present");
        assertEquals(expectedPayload, result.getPayload().get());

    }
}
