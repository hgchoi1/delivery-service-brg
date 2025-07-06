package com.barogo.model.domain.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import com.barogo.model.domain.common.entity.AbstractAuditable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_user_access_token")
public class UserAccessToken extends AbstractAuditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long accessTokenSeq;

  @Column(nullable = false)
  public Long userSeq;

  @Setter
  @Column(unique = true, nullable = false)
  @Comment("엑세스 토큰")
  public String token;

  @Setter
  @Column(unique = true, nullable = false)
  @Comment("리프레시 토큰")
  public String refreshToken;

  @Setter
  @Column(nullable = false)
  @Comment("재발급 여부")
  public boolean revoked;

  @Setter
  @Column(nullable = false)
  @Comment("만료 여부")
  public boolean expired;
}
