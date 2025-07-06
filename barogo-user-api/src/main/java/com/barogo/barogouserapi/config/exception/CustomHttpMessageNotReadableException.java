package com.barogo.barogouserapi.config.exception;

import com.barogo.barogouserapi.config.exception.code.StatusCode;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;

@Getter
public class CustomHttpMessageNotReadableException extends HttpMessageNotReadableException {
    private final StatusCode statusCode;

    public CustomHttpMessageNotReadableException(String message, HttpInputMessage inputMessage, StatusCode statusCode) {
        super(message, inputMessage);
        this.statusCode = statusCode;
    }

    private static HttpInputMessage createHttpInputMessage(StatusCode statusCode) {
        return new HttpInputMessage() {
            @Override
            public InputStream getBody() {
                return new ByteArrayInputStream(statusCode.getCode().getBytes(StandardCharsets.UTF_8));
            }

            @Override
            public HttpHeaders getHeaders() {
                return new HttpHeaders();
            }
        };
    }

    public static void of(StatusCode statusCode) {
        throw new CustomHttpMessageNotReadableException(
            statusCode.getMessage(),
            createHttpInputMessage(statusCode),
            statusCode
        );
    }

    public static void of(StatusCode statusCode, String errorMessage) {
        throw new CustomHttpMessageNotReadableException(
            statusCode.getMessage()+ " (" + errorMessage + ")",
            createHttpInputMessage(statusCode),
            statusCode
        );
    }

}