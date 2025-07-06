package com.barogo.barogouserapi.domain.delivery.service;

import com.barogo.barogouserapi.config.exception.BusinessException;
import com.barogo.barogouserapi.config.exception.code.StatusCode;
import com.barogo.barogouserapi.config.security.CurrentService;
import com.barogo.barogouserapi.domain.common.PageResponse;
import com.barogo.barogouserapi.domain.common.PageResponseHandler;
import com.barogo.barogouserapi.domain.delivery.dto.DeliveryInfoRes;
import com.barogo.barogouserapi.domain.delivery.dto.DeliveryListReq;
import com.barogo.barogouserapi.domain.delivery.dto.DeliveryListRes;
import com.barogo.barogouserapi.domain.delivery.dto.DeliveryUpdateDestinationReq;
import com.barogo.barogouserapi.domain.delivery.repository.DeliveryRepository;
import com.barogo.barogouserapi.domain.rider.repository.RiderRepository;
import com.barogo.barogouserapi.domain.user.repository.UserRepository;
import com.barogo.model.domain.delivery.entity.Delivery;
import com.barogo.model.domain.delivery.type.DeliveryStatus;
import com.barogo.model.domain.delivery.type.DeliveryType;
import com.barogo.model.domain.user.entity.User;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

  private final DeliveryRepository deliveryRepository;
  private final CurrentService currentService;
  private final UserRepository userRepository;
  private final RiderRepository riderRepository;


  /*
   * 전제조건 (3) : 배달 조회 API 를 구현해 주세요.
   * 1.	배달 조회 시, 기간은 필수로 받아주세요.
   *  (또한, 조회 가능한 기간은 최대 3일 입니다. 그 외의 조건도 조회시에 필요하다고 생각되시면 추가해주세요.)
   * 2.	기간 내에 사용자가 주문한 배달의 리스트를 제공합니다.
   */
  @Override
  @Transactional(readOnly = true)
  public PageResponse list(DeliveryListReq deliveryListReq, Pageable pageable) {
    User user = currentService.getCurrentUser();
    Page<DeliveryListRes> list = deliveryRepository.searchPage(user.getUserSeq(), deliveryListReq, pageable);
    return PageResponseHandler.of(list);
  }

  @Override
  @Transactional(readOnly = true)
  public DeliveryInfoRes info(UUID deliveryId) {
    return DeliveryInfoRes.of(getDelivery(deliveryId));
  }

  /*
   * 전제조건 (4) : 배달 주문 수정 API (도착지 주소 변경) 를 구현해 주세요.
   * 1.	사용자로부터 도착지 주소를 요청 받아 처리합니다.
   * 2.	사용자가 변경 가능한 배달인 경우에만 수정이 가능합니다.
   */
  @Override
  @Transactional
  public void updateDestination(UUID deliveryId, DeliveryUpdateDestinationReq deliveryUpdateDestinationReq) {
    Delivery delivery = getDelivery(deliveryId);

    if (!delivery.getStatus().equals(DeliveryStatus.REQUESTED)) {
      throw new BusinessException(StatusCode.DELIVERY_ADDRESS_UPDATE_NOT_ALLOWED);
    }
    deliveryRepository.save(delivery.toBuilder()
                                    .destinationAddr(deliveryUpdateDestinationReq.destination())
                                    .build()
    );
  }

  @Override
  @Transactional
  public void createDelivery() {
    User user = currentService.getCurrentUser();
    var rider = riderRepository.findAll().getFirst();
    deliveryRepository.save(Delivery.builder()
                                    .user(user)
                                    .storeName("맥도날드 강남구청점")
                                    .pickupAddr("서울 강남구 선릉로 667 성우빌딩")
                                    .amount(11000L)
                                    .type(DeliveryType.STANDARD)
                                    .destinationAddr("서울 강남구 언주로134길 32")
                                    .memo("1층에서 연락주세요")
                                    .build());
    rider = riderRepository.findAll().getLast();
    deliveryRepository.save(Delivery.builder()
                                    .user(user)
                                    .rider(rider)
                                    .storeName("써브웨이 선정릉역점")
                                    .pickupAddr("서울 강남구 봉은사로 328 써브웨이 선정릉역점")
                                    .amount(16000L)
                                    .type(DeliveryType.STANDARD)
                                    .destinationAddr("서울 강남구 언주로134길 32")
                                    .status(DeliveryStatus.ASSIGNED)
                                    .memo("도착 전 연락주세요")
                                    .build());
  }

  private Delivery getDelivery(UUID deliveryId) {
    User user = currentService.getCurrentUser();
    Delivery delivery = deliveryRepository.findByDeliveryId(deliveryId)
                                          .orElseThrow(() -> new BusinessException(StatusCode.DELIVERY_NOT_FOUND));
    if (!delivery.getUser().getUserSeq().equals(user.getUserSeq())) {
      throw new BusinessException(StatusCode.DELIVERY_ACCESS_DENIED);
    }
    return delivery;
  }
}
