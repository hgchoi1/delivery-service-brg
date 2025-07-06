package com.barogo.model.domain.user.entity;

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
import com.barogo.model.domain.common.entity.AbstractAuditable;
import com.barogo.model.domain.common.type.Role;
import com.barogo.model.domain.user.type.UserStatus;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_user", indexes = {
    @Index(name = "idx_user_seq", columnList = "user_seq"),
})
public class User extends AbstractAuditable implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userSeq;

  @Column(unique = true, nullable = false)
  @Comment("외부 노출 키")
  private UUID userId;

  @Column(nullable = false)
  @Comment("로그인 아이디")
  private String loginId;

  @Column(nullable = false)
  @Comment("비밀번호")
  private String password;

  @Column(nullable = false)
  @Comment("이름")
  private String name;

  @Column(nullable = false, length = 20)
  @Enumerated(EnumType.STRING)
  @Builder.Default
  @Comment("유저 상태")
  private UserStatus status = UserStatus.ACTIVE;

  // 암호화 필요
  @Comment("핸드폰 번호")
  private String phone;

  @Comment("계정 삭제")
  private LocalDateTime deleteAt;

  @Column(nullable = false, length = 45)
  @Enumerated(EnumType.STRING)
  @Comment("권한")
  private Role role;

  @PrePersist
  public void autofill() {
    if (userId == null) {
      userId = UUID.randomUUID();
    }
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority(this.role.toString()));
    return authorities;
  }

  @Override
  public String getUsername() {
    return loginId;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
