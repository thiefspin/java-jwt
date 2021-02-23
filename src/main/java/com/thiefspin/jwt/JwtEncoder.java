package com.thiefspin.jwt;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

class JwtEncoder {

    static String signHmac(String msg, String key, Algorithm algorithm) throws JwtException {
        try {
            Mac mac = Mac.getInstance(algorithm.getName());
            mac.init(new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), algorithm.getName()));
            return encodeBase64(new String(mac.doFinal(msg.getBytes(StandardCharsets.UTF_8))));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new JwtException(JwtExceptionType.INVALID_ALGORITHM);
        }
    }

    static String encodeBase64(String s) {
        return Base64.encodeBase64URLSafeString(s.getBytes(StandardCharsets.UTF_8));
    }

    static JwtEncodeResult signToken(String encodedHeader, String payload, String secret, Algorithm algorithm) {
        var encodedPayload = JwtEncoder.encodeBase64(payload);
        try {
            var signature = JwtEncoder.signHmac(encodedHeader + encodedPayload, secret, algorithm);
            return new JwtEncodeResult(String.format("%s.%s.%s", encodedHeader, encodedPayload, signature));
        } catch (JwtException e) {
            return new JwtEncodeResult(e);
        }
    }

}
