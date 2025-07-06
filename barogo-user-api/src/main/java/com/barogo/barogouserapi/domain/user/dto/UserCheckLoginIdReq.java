package com.barogo.barogouserapi.domain.user.dto;

import com.barogo.barogouserapi.config.exception.code.StatusCode;
import com.barogo.barogouserapi.config.exception.handler.CustomBeanInstantiationException;
import com.barogo.util.RegexUtils;

public record UserCheckLoginIdReq(
    String loginId
) {
  public UserCheckLoginIdReq {
    validateLoginId(loginId);
  }

  private void validateLoginId(String input) {
    if (input == null) {
      throw new CustomBeanInstantiationException(StatusCode.INVALID_INPUT);
    }
    if (!RegexUtils.isValidLoginId(input)) {
      throw new CustomBeanInstantiationException(StatusCode.LOGIN_ID_INVALID_FORMAT);
    }
  }
}
