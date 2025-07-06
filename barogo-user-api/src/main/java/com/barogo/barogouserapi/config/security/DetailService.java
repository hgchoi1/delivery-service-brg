package com.barogo.barogouserapi.config.security;


import com.barogo.barogouserapi.config.exception.BusinessException;
import com.barogo.barogouserapi.config.exception.code.StatusCode;
import com.barogo.barogouserapi.domain.user.repository.UserRepository;
import com.barogo.model.domain.common.type.Role;
import com.barogo.model.domain.user.entity.User;
import com.barogo.model.domain.user.type.UserStatus;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service(value = "detailService")
@RequiredArgsConstructor
public class DetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        User user = userRepository.findByLoginIdAndRole(loginId, Role.USER)
                              .orElseThrow(() -> new BusinessException(StatusCode.LOGIN_ERROR_INVALID_CREDENTIALS));
        if (!user.getStatus().equals(UserStatus.ACTIVE)) {
            throw new BusinessException(StatusCode.LOGIN_ERROR_ACCOUNT_DISABLED);
        }
        return user;
    }

}
