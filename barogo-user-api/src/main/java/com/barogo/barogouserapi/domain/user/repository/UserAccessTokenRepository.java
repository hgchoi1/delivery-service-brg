package com.barogo.barogouserapi.domain.user.repository;

import com.barogo.model.domain.user.entity.UserAccessToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccessTokenRepository extends JpaRepository<UserAccessToken, Long>, UserAccessTokenRepositoryCustom {
  Optional<UserAccessToken> findByToken(String token);
}
