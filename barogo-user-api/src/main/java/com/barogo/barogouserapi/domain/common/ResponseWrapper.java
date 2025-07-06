package com.barogo.barogouserapi.domain.common;

import static com.barogo.barogouserapi.config.exception.code.StatusCode.OK;

import com.fasterxml.jackson.annotation.JsonProperty;
public record ResponseWrapper<T>(int status, String code, @JsonProperty("data") T content) {

    public static <T> ResponseWrapper<T> of(T content) {
        return new ResponseWrapper<>(OK.getStatus().value(), OK.getCode(), content);
    }
}