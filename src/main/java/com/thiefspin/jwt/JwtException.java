package com.thiefspin.jwt;

public class JwtException extends Exception {

    JwtException(JwtExceptionType type) {
        super(type.getMessage());
    }


}
