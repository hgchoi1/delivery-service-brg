package com.barogo.barogouserapi.config.exception.handler;


import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import com.barogo.barogouserapi.config.exception.BusinessException;
import com.barogo.barogouserapi.config.exception.CustomHttpMessageNotReadableException;
import com.barogo.barogouserapi.config.exception.code.StatusCode;
import java.nio.file.AccessDeniedException;
import javax.security.sasl.AuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    private final static String ERROR_CODE_PROPERTY_NAME = "code";


    /**
     * BusinessException : 사용자 정의 오류
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleException(BusinessException e) {
        var problemDetail = ProblemDetail.forStatusAndDetail(e.getStatus(), e.getDetails());
        problemDetail.setProperty(ERROR_CODE_PROPERTY_NAME, e.getCode());

        return new ResponseEntity<>(problemDetail, e.getStatus());
    }

    /**
     * CustomHttpMessageNotReadableException : RequestBody
     */
    @ExceptionHandler({CustomHttpMessageNotReadableException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<?> handleException(CustomHttpMessageNotReadableException e) {
        var problemDetail = ProblemDetail.forStatusAndDetail(e.getStatusCode().getStatus(), e.getMessage());
        problemDetail.setProperty(ERROR_CODE_PROPERTY_NAME, e.getStatusCode().getCode());
        problemDetail.setDetail(e.getStatusCode().getMessage());

        return new ResponseEntity<>(problemDetail, e.getStatusCode().getStatus());
    }

    /**
     * CustomBeanInstantiationException : ModelAttribute
     */
    @ExceptionHandler({CustomBeanInstantiationException.class, BeanInstantiationException.class})
    public ResponseEntity<?> handleException(CustomBeanInstantiationException e) {
        var problemDetail = ProblemDetail.forStatusAndDetail(e.getStatusCode().getStatus(), e.getMessage());
        problemDetail.setProperty(ERROR_CODE_PROPERTY_NAME, e.getStatusCode().getCode());
        problemDetail.setDetail(e.getStatusCode().getMessage());

        return new ResponseEntity<>(problemDetail, e.getStatusCode().getStatus());
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<?> authenticationException(Exception e) {
        log.error("AUTHENTICATION EXCEPTION : {}", e.getMessage());
        var problemDetail = ProblemDetail.forStatusAndDetail(UNAUTHORIZED, e.getMessage());
        return new ResponseEntity<>(problemDetail, UNAUTHORIZED);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<?> accessDenied(Exception e) {
        log.error("ACCESS DENIED : {}", e.getMessage());
        var problemDetail = ProblemDetail.forStatusAndDetail(FORBIDDEN, e.getMessage());
        return new ResponseEntity<>(problemDetail, FORBIDDEN);
    }

    /**
     * Exception : 정의되지 않은 모든 에러
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {

        log.error("GLOBAL EXCEPTION : {}", e);
        var err = StatusCode.NOT_DEFINED_ERR;
        try {
            var problemDetail = ProblemDetail.forStatusAndDetail(err.getStatus(), err.getMessage());
            problemDetail.setProperty(ERROR_CODE_PROPERTY_NAME, err.getCode());
            return new ResponseEntity<>(problemDetail, err.getStatus());
        } catch (Exception ex) {
            return new ResponseEntity<>(err.getMessage(), err.getStatus());
        }
    }

}
