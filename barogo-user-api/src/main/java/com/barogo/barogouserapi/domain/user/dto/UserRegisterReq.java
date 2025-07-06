package com.barogo.barogouserapi.domain.user.dto;

import com.barogo.barogouserapi.config.exception.CustomHttpMessageNotReadableException;
import com.barogo.barogouserapi.config.exception.code.StatusCode;
import com.barogo.model.domain.common.type.Role;
import com.barogo.model.domain.user.entity.User;
import com.barogo.util.RegexUtils;

public record UserRegisterReq(
    String name,
    String phone,
    String loginId,
    String password
) {
    public UserRegisterReq {
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
            CustomHttpMessageNotReadableException.of(StatusCode.PHONE_INVALID_FORMAT);
        }
    }

    private void validateName(String input) {
        if (input == null ) {
            CustomHttpMessageNotReadableException.of(StatusCode.INVALID_INPUT);
        }
        if (!(RegexUtils.isValidKoreanName(input) || RegexUtils.isValidForeignName(input))){
            CustomHttpMessageNotReadableException.of(StatusCode.NAME_INVALID_FORMAT);
        }
    }

    private void validateLoginId(String input) {
        if (input == null ) {
            CustomHttpMessageNotReadableException.of(StatusCode.INVALID_INPUT);
        }
        if (!RegexUtils.isValidLoginId(input)) {
            CustomHttpMessageNotReadableException.of(StatusCode.LOGIN_ID_INVALID_FORMAT);
        }
    }

    private void validatePassword(String input) {
        if (input == null ) {
            CustomHttpMessageNotReadableException.of(StatusCode.INVALID_INPUT);
        }
        if (!RegexUtils.isValidPassword(input)) {
            CustomHttpMessageNotReadableException.of(StatusCode.PASSWORD_INVALID_FORMAT);
        }
    }

    public User toEntity(String password) {
        return User.builder()
                   .loginId(loginId)
                   .password(password)
                    .phone(phone)
                   .name(name)
                   .role(Role.USER)
                   .build();
    }
}
