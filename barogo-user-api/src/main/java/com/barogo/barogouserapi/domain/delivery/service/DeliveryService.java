package com.barogo.barogouserapi.domain.delivery.service;

import com.barogo.barogouserapi.domain.common.PageResponse;
import com.barogo.barogouserapi.domain.delivery.dto.DeliveryInfoRes;
import com.barogo.barogouserapi.domain.delivery.dto.DeliveryListReq;
import com.barogo.barogouserapi.domain.delivery.dto.DeliveryUpdateDestinationReq;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

public interface DeliveryService {
  // 리스트 조회
  PageResponse list(DeliveryListReq deliveryListReq, Pageable pageable);
  // 단건조회
  DeliveryInfoRes info(UUID deliveryId);
  // 배송지 변경
  void updateDestination(UUID deliveryId, DeliveryUpdateDestinationReq deliveryUpdateDestinationReq);
  // 주문 생성(더미)
  void createDelivery();
}
