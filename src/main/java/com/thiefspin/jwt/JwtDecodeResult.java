package com.thiefspin.jwt;

import lombok.Getter;

import java.util.Optional;
import java.util.function.Consumer;

@Getter
public class JwtDecodeResult {

    private final Optional<String> payload;
    private final Optional<JwtHeader> header;
    private final Optional<JwtException> exception;

    JwtDecodeResult(String payload, JwtHeader header) {
        this.payload = Optional.of(payload);
        this.header = Optional.of(header);
        this.exception = Optional.empty();
    }

    JwtDecodeResult(JwtException exception) {
        this.payload = Optional.empty();
        this.header = Optional.empty();
        this.exception = Optional.of(exception);
    }
}
