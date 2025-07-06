package com.barogo.barogouserapi.config.exception.handler;

import com.barogo.barogouserapi.config.exception.code.StatusCode;
import lombok.Getter;

@Getter
public class CustomBeanInstantiationException extends RuntimeException {
    private final StatusCode statusCode;

    public CustomBeanInstantiationException(StatusCode statusCode) {
        super(statusCode.getMessage());
        this.statusCode = statusCode;
    }

    public CustomBeanInstantiationException(StatusCode statusCode, String errorMessage) {
        super(statusCode.getMessage() + " (" + errorMessage + ")");
        this.statusCode = statusCode;
    }
}