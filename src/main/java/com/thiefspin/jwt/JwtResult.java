package com.thiefspin.jwt;

import lombok.Getter;

import java.util.Optional;
import java.util.function.Consumer;

@Getter
public class JwtResult {

    private final Optional<String> token;
    private final Optional<JwtException> exception;

    JwtResult(String token) {
        this.token = Optional.of(token);
        this.exception = Optional.empty();
    }

    JwtResult(JwtException exception) {
        this.token = Optional.empty();
        this.exception = Optional.of(exception);
    }

    void foreach(Consumer<String> f) {
        token.ifPresent(f);
    }
}
