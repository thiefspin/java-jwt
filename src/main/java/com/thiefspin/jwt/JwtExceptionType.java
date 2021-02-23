package com.thiefspin.jwt;

import lombok.AllArgsConstructor;

@AllArgsConstructor
enum JwtExceptionType {

    TOO_MANY_SEGMENTS("Too many segments"),
    NOT_ENOUGH_SEGMENTS("Not enough segments"),
    INVALID_ALGORITHM("Invalid algorithm specified"),
    FAILED_TO_ENCODE_HEADER("Failed to encode header part");

    private final String message;

    String getMessage() {
        return this.message;
    }
}
