package com.barogo.barogouserapi.delivery;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.barogo.barogouserapi.config.exception.BusinessException;
import com.barogo.barogouserapi.config.exception.code.StatusCode;
import com.barogo.barogouserapi.config.security.DetailService;
import com.barogo.barogouserapi.domain.delivery.dto.DeliveryUpdateDestinationReq;
import com.barogo.barogouserapi.domain.delivery.repository.DeliveryRepository;
import com.barogo.barogouserapi.domain.delivery.service.DeliveryService;
import com.barogo.barogouserapi.domain.rider.dto.RiderRegisterReq;
import com.barogo.barogouserapi.domain.rider.repository.RiderRepository;
import com.barogo.barogouserapi.domain.rider.service.RiderService;
import com.barogo.barogouserapi.domain.user.dto.UserLoginReq;
import com.barogo.barogouserapi.domain.user.dto.UserRegisterReq;
import com.barogo.barogouserapi.domain.user.repository.UserRepository;
import com.barogo.barogouserapi.domain.user.service.UserService;
import com.barogo.model.domain.delivery.entity.Delivery;
import com.barogo.model.domain.delivery.type.DeliveryStatus;
import com.barogo.model.domain.delivery.type.DeliveryType;
import com.barogo.model.domain.rider.entity.Rider;
import com.barogo.model.domain.user.entity.User;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class DeliveryTest {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private RiderRepository riderRepository;
  @Autowired
  private DeliveryRepository deliveryRepository;
  @Autowired
  private DeliveryService deliveryService;
  @Autowired
  private UserService userService;
  @Autowired
  private RiderService riderService;
  @Autowired
  private DetailService detailService;
  private UUID successId; // 테스트 대상 주문 ID
  private UUID failId; // 테스트 대상 주문 ID

  @BeforeEach
  void setup() {
    //  given: 더미 데이터 생성
    userService.register(
        new UserRegisterReq(
            "박엘지",
            "01012349876",
            "parklg",
            "parklg9876!!!"
        )
    );
    userService.register(
        new UserRegisterReq(
            "김삼성",
            "01011002299",
            "kimsamsung",
            "Kimsamsung2299"
        )
    );
    riderService.register(
        new RiderRegisterReq(
            "김무개",
            "01098127634",
            "kimkorean",
            "Kimkorean7634!"
        )
    );
    User user = userRepository.findByLoginId("kimsamsung").get();
    Rider rider = riderRepository.findAll().getFirst();
    Delivery delivery = deliveryRepository.save(Delivery.builder()
                                    .user(user)
                                    .rider(rider)
                                    .storeName("무신사")
                                    .pickupAddr("서울 성동구 연무장길 83 1F-2F")
                                    .amount(250000L)
                                    .type(DeliveryType.EXPRESS)
                                    .destinationAddr("서울 강남구 언주로134길 32")
                                    .status(DeliveryStatus.DELIVERING)
                                    .memo("도착 전 연락주세요")
                                    .build());
    failId = delivery.getDeliveryId();
    delivery = deliveryRepository.save(Delivery.builder()
                                    .user(user)
                                    .storeName("무신사")
                                    .pickupAddr("서울 성동구 연무장길 83 1F-2F")
                                    .amount(140000L)
                                    .type(DeliveryType.STANDARD)
                                    .destinationAddr("서울특별시 성동구 아차산로13길 11 무신사 캠퍼스 N1")
                                    .status(DeliveryStatus.REQUESTED)
                                    .build());
    successId = delivery.getDeliveryId();
  }

  @Test
  void updateDestination_성공() {
    // given:
    UserDetails userDetail = detailService.loadUserByUsername("kimsamsung");
    Authentication authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authentication);

    // when:
    String newDestination ="서울 용산구 남산공원길 105";
    assertDoesNotThrow(() -> deliveryService.updateDestination(successId,
        new DeliveryUpdateDestinationReq(
            newDestination
        ))
    );

    // then:
    Delivery delivery = deliveryRepository.findByDeliveryId(successId).get();
    assertEquals(newDestination, delivery.getDestinationAddr()); // 주소가 잘 바뀌었는지 확인
  }

  @Test
  void updateDestination_실패_다른회원의_주문_접근() {
    // given:
    UserDetails userDetail = detailService.loadUserByUsername("parklg");
    Authentication authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authentication);

    // when & then:
    String newDestination ="서울 용산구 남산공원길 105";
    BusinessException ex = assertThrows(BusinessException.class, () -> {
      deliveryService.updateDestination(successId,
          new DeliveryUpdateDestinationReq(
              newDestination)
      );
    });
    assertEquals(StatusCode.DELIVERY_ACCESS_DENIED.getCode(), ex.getCode());
  }



  @Test
  void updateDestination_실패_주문의_변경불가한상태() {
    // given:
    UserDetails userDetail = detailService.loadUserByUsername("kimsamsung");
    Authentication authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authentication);

    // when & then:
    String newDestination ="서울 용산구 남산공원길 105";
    BusinessException ex = assertThrows(BusinessException.class, () -> {
      deliveryService.updateDestination(failId,
          new DeliveryUpdateDestinationReq(
              newDestination)
      );
    });
    assertEquals(StatusCode.DELIVERY_ADDRESS_UPDATE_NOT_ALLOWED.getCode(), ex.getCode());
  }
}
