package com.barogo.barogouserapi.domain.delivery.repository;

import com.barogo.barogouserapi.domain.delivery.dto.DeliveryListReq;
import com.barogo.barogouserapi.domain.delivery.dto.DeliveryListRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QDeliveryRepository {

  Page<DeliveryListRes> searchPage(Long userSeq, DeliveryListReq condition, Pageable pageable);
}
