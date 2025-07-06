package com.barogo.barogouserapi.domain.delivery.dto;

import com.barogo.model.domain.delivery.entity.Delivery;
import com.barogo.model.domain.delivery.type.DeliveryStatus;
import com.barogo.model.domain.delivery.type.DeliveryType;
import java.time.LocalDateTime;
import java.util.UUID;

public record DeliveryInfoRes(
    UUID deliveryId, // 고유 키
    LocalDateTime delivaryAt, // 주문일시
    DeliveryStatus deliveryStatus,  // 상태
    DeliveryType deliveryType,  // 배달 타입
    String storeName, // 가게명
    Long amount, // 주문 금액
    String destination, // 도착지
    String memo // 메모
) {

  public static DeliveryInfoRes of(Delivery delivery) {
    return new DeliveryInfoRes(
        delivery.getDeliveryId(),
        delivery.getCreatedAt(),
        delivery.getStatus(),
        delivery.getType(),
        delivery.getStoreName(),
        delivery.getAmount(),
        delivery.getDestinationAddr(),
        delivery.getMemo()
    );
  }
}
