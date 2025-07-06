package com.barogo.barogouserapi.domain.delivery.dto;

import com.barogo.barogouserapi.config.exception.CustomHttpMessageNotReadableException;
import com.barogo.barogouserapi.config.exception.code.StatusCode;

public record DeliveryUpdateDestinationReq(
    String destination
) {
  public DeliveryUpdateDestinationReq{
    validateInput(destination);
  }

  private void validateInput(String input) {
    if (input == null || input.isEmpty()) {
      CustomHttpMessageNotReadableException.of(StatusCode.INVALID_INPUT);
    }
    if (input.length() < 10) {
      CustomHttpMessageNotReadableException.of(StatusCode.DELIVERY_ADDRESS_UPDATE_ERROR);
    }
  }
}
