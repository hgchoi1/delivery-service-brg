package com.barogo.barogouserapi.domain.user.repository;

import com.barogo.model.domain.common.type.Role;
import com.barogo.model.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByLoginId(String loginId);
  Optional<User> findByLoginIdAndRole(String loginId, Role role);
  Optional<User> findByUserSeq(Long userSeq);
}
