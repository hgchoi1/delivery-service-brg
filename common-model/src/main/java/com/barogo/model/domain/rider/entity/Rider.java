package com.barogo.model.domain.rider.entity;

import com.barogo.model.domain.common.entity.AbstractAuditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import com.barogo.model.domain.rider.type.RiderStatus;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_rider", indexes = {
    @Index(name = "idx_rider_seq", columnList = "rider_seq"),
})
public class Rider extends AbstractAuditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long riderSeq;

  @Column(unique = true, nullable = false)
  @Comment("외부 노출 키")
  private UUID riderId;

  @Column(nullable = false)
  @Comment("로그인 아이디")
  private String loginId;

  @Column(nullable = false)
  @Comment("비밀번호")
  private String password;

  @Column(nullable = false)
  @Comment("이름")
  private String name;

  // 암호화 필요
  @Comment("핸드폰 번호")
  private String phone;

  @Column(nullable = false, length = 20)
  @Enumerated(EnumType.STRING)
  @Builder.Default
  private RiderStatus status = RiderStatus.ACTIVE;

  @PrePersist
  public void autofill() {
    if (riderId == null) {
      riderId = UUID.randomUUID();
    }
  }
}
