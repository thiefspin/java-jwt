package com.thiefspin.jwt;

import java.util.Optional;

public class Jwt {

    static JwtResult encode(String secret, String payload) {
        return encode(secret, payload, Optional.empty(), Optional.empty());
    }

    static JwtResult encode(String secret, String payload, String header) {
        return encode(secret, payload, Optional.of(header), Optional.empty());
    }

    static JwtResult encode(String secret, String payload, Algorithm algorithm) {
        return encode(secret, payload, Optional.empty(), Optional.of(algorithm));
    }

    static JwtResult encode(String secret, String payload, Optional<String> header, Optional<Algorithm> algorithm) {
        final var alg = algorithm.orElse(Algorithm.HS256);

        return encodeHeader(alg, header).map(encodedHeader -> signToken(encodedHeader, payload, secret, alg))
            .orElse(new JwtResult(new JwtException(JwtExceptionType.FAILED_TO_ENCODE_HEADER)));
    }

    private static JwtResult signToken(String encodedHeader, String payload, String secret, Algorithm algorithm) {
        var encodedPayload = JwtEncoder.encodeBase64(payload);
        try {
            var signature = JwtEncoder.signHmac(encodedHeader + encodedPayload, secret, algorithm);
            return new JwtResult(String.format("%s.%s.%s", encodedHeader, encodedPayload, signature));
        } catch (JwtException e) {
            return new JwtResult(e);
        }
    }

    protected static Optional<String> encodeHeader(Algorithm algorithm, Optional<String> extraHeader) {
        return getHeaderJson(algorithm, extraHeader)
            .stream()
            .map(JwtEncoder::encodeBase64)
            .findFirst();
    }

    protected static Optional<String> getHeaderJson(Algorithm algorithm, Optional<String> extraHeader) {
        return extraHeader
            .map(header -> new JwtHeader(algorithm, header).toJson())
            .orElse(new JwtHeader(algorithm).toJson());
    }

}
