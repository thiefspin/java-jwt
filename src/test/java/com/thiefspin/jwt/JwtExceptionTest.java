package com.thiefspin.jwt;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JwtExceptionTest {

    @Test
    @DisplayName("throwing a JwtException should yield the correct message")
    void testJwtException() {
        assertExceptionMessage(JwtExceptionType.NOT_ENOUGH_SEGMENTS);
        assertExceptionMessage(JwtExceptionType.TOO_MANY_SEGMENTS);
        assertExceptionMessage(JwtExceptionType.INVALID_ALGORITHM);
        assertExceptionMessage(JwtExceptionType.FAILED_TO_ENCODE_HEADER);
        assertExceptionMessage(JwtExceptionType.FAILED_TO_DECODE_HEADER);
        assertExceptionMessage(JwtExceptionType.INVALID_SIGNATURE);
        assertExceptionMessage(JwtExceptionType.FAILED_TO_PARSE_HEADER);
    }

    private void assertExceptionMessage(JwtExceptionType type) {
        assertTrue(
            new JwtException(type)
                .getMessage()
                .contains(type.getMessage())
        );
    }
}
