package com.thiefspin.jwt;

import java.util.Optional;

public class Jwt {

    public static JwtEncodeResult encode(String secret, String payload) {
        return encode(secret, payload, Optional.empty(), Optional.empty());
    }

    public static JwtEncodeResult encode(String secret, String payload, String header) {
        return encode(secret, payload, Optional.of(header), Optional.empty());
    }

    public static JwtEncodeResult encode(String secret, String payload, Algorithm algorithm) {
        return encode(secret, payload, Optional.empty(), Optional.of(algorithm));
    }

    public static JwtEncodeResult encode(String secret, String payload, Optional<String> header, Optional<Algorithm> algorithm) {
        final var alg = algorithm.orElse(Algorithm.HS256);

        return encodeHeader(alg, header)
            .map(encodedHeader -> JwtEncoder.signToken(encodedHeader, payload, secret, alg))
            .orElse(new JwtEncodeResult(new JwtException(JwtExceptionType.FAILED_TO_ENCODE_HEADER)));
    }

//    public static JwtDecodeResult decode(String token, String secret) {
//        if (token.isBlank()) return new JwtDecodeResult(new JwtException(JwtExceptionType.NOT_ENOUGH_SEGMENTS));
//
//    }

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
