package com.thiefspin.jwt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JwtResultTest {

    @Test
    @DisplayName("JwtResult should be created correctly with token as result")
    void testJwtResultCreationWithToken() {
        final var token = "token";
        final var result = new JwtResult(token);
        assertTrue(result.getException().isEmpty());
        assertEquals(token, result.getToken().get());
    }

    @Test
    @DisplayName("JwtResult should be created correctly with exception as result")
    void testJwtResultCreationWithException() {
        final var ex = new JwtException(JwtExceptionType.INVALID_ALGORITHM);
        final var result = new JwtResult(ex);
        assertTrue(result.getToken().isEmpty());
        assertEquals(ex.getMessage(), result.getException().get().getMessage());
    }

    @Test
    @DisplayName("JwtResult should have a working map function")
    void testJwtResultMapFunction() {
        final var token = "token";
        final var result = new JwtResult(token);
        result.foreach(t -> assertEquals(token, t));
    }
}
