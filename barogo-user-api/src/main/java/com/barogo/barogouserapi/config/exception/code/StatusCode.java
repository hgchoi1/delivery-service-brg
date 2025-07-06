package com.barogo.barogouserapi.config.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum StatusCode {

    // [Success] 0000 ~ 0999
    OK(HttpStatus.OK, "0000", "Ok"),

    // [User] 1000 ~ 1999
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "1001", "회원정보가 존재하지 않습니다."),
    DUPLICATED_LOGIN_ID(HttpStatus.BAD_REQUEST, "1002", "중복된 아이디입니다."),
    LOGIN_ERROR_INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "1003", "아이디 혹은 비밀번호가 존재하지 않습니다."),
    LOGIN_ERROR_ACCOUNT_DISABLED(HttpStatus.UNAUTHORIZED, "1004", "로그인이 제한된 계정입니다."),
    LOGIN_ID_INVALID_FORMAT(HttpStatus.BAD_REQUEST, "1005", "아이디 형식을 만족하지 않습니다."),
    PASSWORD_INVALID_FORMAT(HttpStatus.BAD_REQUEST, "1006", "비밀번호가 형식을 만족하지 않습니다."),
    NAME_INVALID_FORMAT(HttpStatus.BAD_REQUEST, "1007", "이름이 형식을 만족하지 않습니다."),
    PHONE_INVALID_FORMAT(HttpStatus.BAD_REQUEST, "1008", "휴대폰이 형식을 만족하지 않습니다."),

    // [Rider] 2000 ~ 2999
    RIDER_NOT_FOUND(HttpStatus.BAD_REQUEST, "2001", "라이더정보가 존재하지 않습니다."),
    RIDER_DUPLICATED_LOGIN_ID(HttpStatus.BAD_REQUEST, "1002", "중복된 아이디입니다."),
    RIDER_LOGIN_ID_INVALID_FORMAT(HttpStatus.BAD_REQUEST, "2002", "아이디 형식을 만족하지 않습니다."),
    RIDER_PASSWORD_INVALID_FORMAT(HttpStatus.BAD_REQUEST, "2003", "비밀번호가 형식을 만족하지 않습니다."),
    RIDER_NAME_INVALID_FORMAT(HttpStatus.BAD_REQUEST, "2004", "이름이 형식을 만족하지 않습니다."),
    RIDER_PHONE_INVALID_FORMAT(HttpStatus.BAD_REQUEST, "2005", "휴대폰이 형식을 만족하지 않습니다."),

    // [Delivery] 3000 ~ 3999
    DELIVERY_NOT_FOUND(HttpStatus.BAD_REQUEST, "2001", "주문정보가 존재하지 않습니다."),
    DELIVERY_INVALID_DATE(HttpStatus.BAD_REQUEST, "2002", "조회 기간을 다시 확인해주세요.(최대 3일)"),
    DELIVERY_ADDRESS_UPDATE_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "2003", "배송지 수정이 불가합니다."),
    DELIVERY_ADDRESS_UPDATE_ERROR(HttpStatus.BAD_REQUEST, "2004", "변경할 배송지를 다시 확인해주세요."),
    DELIVERY_ACCESS_DENIED(HttpStatus.FORBIDDEN, "2005","주문에 접근할 수 없습니다."),

    // [Common] 8000 ~ 8999
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "8001", "입력값이 유효하지 않습니다."),

    // [System Error] 9000 ~ 9999
    BAD_REQUEST(HttpStatus.BAD_REQUEST,"9001", "Bad request"),
    NOT_DEFINED_ERR(HttpStatus.INTERNAL_SERVER_ERROR, "9999", "An error occurred during the service");

    private final HttpStatus status;
    private final String code;
    private final String message;
}