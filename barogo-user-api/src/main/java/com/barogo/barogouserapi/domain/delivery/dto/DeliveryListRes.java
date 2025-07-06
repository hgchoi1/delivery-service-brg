package com.barogo.barogouserapi.domain.delivery.dto;

import com.barogo.model.domain.delivery.type.DeliveryStatus;
import com.barogo.model.domain.delivery.type.DeliveryType;

import java.time.LocalDateTime;
import java.util.UUID;

public record DeliveryListRes(
    UUID deliveryId, // 고유 키
    LocalDateTime deliveryAt, // 주문일시
    DeliveryStatus deliveryStatus,  // 상태
    DeliveryType deliveryType,  // 배달 타입
    String storeName, // 가게명
    Long amount // 주문 금액
) {
}
