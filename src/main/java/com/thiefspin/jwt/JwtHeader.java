package com.thiefspin.jwt;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.util.Optional;

@Getter
@JsonPropertyOrder({"alg", "extraHeader"})
public class JwtHeader {

    private final Algorithm alg;
    @JsonRawValue
    private String extraHeader;
    private final String type;

    JwtHeader(Algorithm alg) {
        this.alg = alg;
        this.type = "JWT";
    }

    JwtHeader(Algorithm alg, String header) {
        this.alg = alg;
        this.type = "JWT";
        this.extraHeader = header;
    }

    Optional<String> toJson() {
        try {
            return Optional.of(new ObjectMapper().writeValueAsString(this));
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
    }
}
