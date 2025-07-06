package com.barogo.model.domain.delivery.entity;

import com.barogo.model.domain.common.entity.AbstractAuditable;
import com.barogo.model.domain.rider.entity.Rider;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import com.barogo.model.domain.delivery.type.DeliveryStatus;
import com.barogo.model.domain.delivery.type.DeliveryType;
import com.barogo.model.domain.user.entity.User;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "tb_delivery", indexes = {
    @Index(name = "idx_delivery_seq", columnList = "delivery_seq")
})
public class Delivery extends AbstractAuditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long deliverySeq;

  @Column(unique = true, nullable = false)
  @Comment("외부 노출 키")
  private UUID deliveryId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_seq", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "rider_seq", nullable = true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Rider rider;

  @Comment("가게명")
  private String storeName;

  @Comment("주문 금액")
  private Long amount;

  @Column(nullable = false, length = 20)
  @Enumerated(EnumType.STRING)
  @Builder.Default
  @Comment("상태")
  private DeliveryStatus status = DeliveryStatus.REQUESTED;

  @Column(nullable = false, length = 20)
  @Enumerated(EnumType.STRING)
  @Comment("배달타입")
  private DeliveryType type;

  @Column(nullable = false)
  @Comment("픽업지")
  private String pickupAddr;
  //private Double pickupLat;
  //private Double pickupLon;

  @Column(nullable = false)
  @Comment("도착지")
  private String destinationAddr;
  //private Double destinationLon;
  //private Double destinationLat;

  @Comment("요청사항")
  @Column(length = 50)
  private String memo;

  @PrePersist
  public void autofill() {
    if (deliveryId == null) {
      deliveryId = UUID.randomUUID();
    }
  }
}
