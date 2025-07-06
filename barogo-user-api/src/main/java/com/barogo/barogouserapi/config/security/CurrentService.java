package com.barogo.barogouserapi.config.security;

import com.barogo.barogouserapi.config.exception.BusinessException;
import com.barogo.barogouserapi.config.exception.code.StatusCode;
import com.barogo.barogouserapi.domain.user.repository.UserRepository;

import com.barogo.model.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentService {

  private final UserRepository userRepository;

  public User getCurrentUser() {

    return userRepository.findByUserSeq(getAuthenticatedUser().getUserSeq()).orElseThrow(() -> new BusinessException(
        StatusCode.USER_NOT_FOUND));
  }

  public User getAuthenticatedUser() {
    return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }
}
