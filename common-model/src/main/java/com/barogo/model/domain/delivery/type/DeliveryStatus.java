package com.barogo.model.domain.delivery.type;

public enum DeliveryStatus {
  REQUESTED,    // 최초 주문 생성
  ASSIGNED,     // 라이더 배정
  PICKED_UP,    // 라이더 픽업
  DELIVERING,   // 배달 중
  DELIVERED,    // 배달 완료
  CANCELED,     // 주문 취소
  FAILED        // 실패
  ;
}
