package com.barogo.barogouserapi.domain.delivery.dto;

import com.barogo.barogouserapi.config.exception.CustomHttpMessageNotReadableException;
import com.barogo.barogouserapi.config.exception.code.StatusCode;
import java.time.LocalDate;


public record DeliveryListReq(
    Integer page,
    Integer size,
    LocalDate startDt,
    LocalDate endDt
) {

  public DeliveryListReq{
    validateDtRange(startDt,endDt);
  }

  private void validateDtRange(LocalDate startDt, LocalDate endDt) {
    if (startDt == null || endDt == null) {
      CustomHttpMessageNotReadableException.of(StatusCode.INVALID_INPUT);
    }
    if (endDt.isBefore(startDt)) {
      CustomHttpMessageNotReadableException.of(StatusCode.DELIVERY_INVALID_DATE);
    }
    if (endDt.isAfter(startDt.plusDays(3))) {
      CustomHttpMessageNotReadableException.of(StatusCode.DELIVERY_INVALID_DATE);
    }
  }
}
