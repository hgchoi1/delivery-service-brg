package com.barogo.barogouserapi.config.exception;

import com.barogo.barogouserapi.config.exception.code.StatusCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {

  private final HttpStatus status;

  private final String code;

  private final String details;

  public BusinessException(StatusCode statusCode) {
    super(statusCode.getMessage());
    this.status = statusCode.getStatus();
    this.code = statusCode.getCode();
    this.details = statusCode.getMessage();
  }

}
