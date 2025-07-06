package com.barogo.barogouserapi.domain.rider.dto;

import com.barogo.barogouserapi.config.exception.CustomHttpMessageNotReadableException;
import com.barogo.barogouserapi.config.exception.code.StatusCode;
import com.barogo.model.domain.rider.entity.Rider;
import com.barogo.util.RegexUtils;

public record RiderRegisterReq(
    String name,
    String phone,
    String loginId,
    String password
) {
    public RiderRegisterReq {
        validateName(name);
        validatePhone(phone);
        validateLoginId(loginId);
        validatePassword(password);
    }

    private void validatePhone(String input) {
        if (input == null ) {
            CustomHttpMessageNotReadableException.of(StatusCode.INVALID_INPUT);
        }
        if (!RegexUtils.isValidPhone(input)) {
            CustomHttpMessageNotReadableException.of(StatusCode.RIDER_PHONE_INVALID_FORMAT);
        }
    }

    private void validateName(String input) {
        if (input == null ) {
            CustomHttpMessageNotReadableException.of(StatusCode.INVALID_INPUT);
        }
        if (!(RegexUtils.isValidKoreanName(input) || RegexUtils.isValidForeignName(input))){
            CustomHttpMessageNotReadableException.of(StatusCode.RIDER_NAME_INVALID_FORMAT);
        }
    }

    private void validateLoginId(String input) {
        if (input == null ) {
            CustomHttpMessageNotReadableException.of(StatusCode.INVALID_INPUT);
        }
        if (!RegexUtils.isValidLoginId(input)) {
            CustomHttpMessageNotReadableException.of(StatusCode.RIDER_LOGIN_ID_INVALID_FORMAT);
        }
    }

    private void validatePassword(String input) {
        if (input == null ) {
            CustomHttpMessageNotReadableException.of(StatusCode.INVALID_INPUT);
        }
        if (!RegexUtils.isValidPassword(input)) {
            CustomHttpMessageNotReadableException.of(StatusCode.RIDER_PASSWORD_INVALID_FORMAT);
        }
    }

    public Rider toEntity(String password) {
        return Rider.builder()
                   .loginId(loginId)
                   .password(password)
                   .phone(phone)
                   .name(name)
                   .build();
    }
}
