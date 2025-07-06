package com.barogo.barogouserapi.domain.user.dto;

import com.barogo.barogouserapi.config.exception.CustomHttpMessageNotReadableException;
import com.barogo.barogouserapi.config.exception.code.StatusCode;
import com.barogo.util.RegexUtils;

public record UserLoginReq(
    String loginId,
    String password
) {
  public UserLoginReq{
    validateLoginId(loginId);
    validatePassword(password);
  }

  private void validatePassword(String input) {
    if (input.isBlank()) {
      CustomHttpMessageNotReadableException.of(StatusCode.INVALID_INPUT);
    }
    if (!RegexUtils.isValidPassword(input)) {
      CustomHttpMessageNotReadableException.of(StatusCode.PASSWORD_INVALID_FORMAT);
    }
  }

  private void validateLoginId(String input) {
    if (input.isBlank()) {
      CustomHttpMessageNotReadableException.of(StatusCode.INVALID_INPUT);
    }
    if (!RegexUtils.isValidLoginId(input)) {
      CustomHttpMessageNotReadableException.of(StatusCode.LOGIN_ID_INVALID_FORMAT);
    }
  }
}

