package com.barogo.barogouserapi.domain.rider.service;

import com.barogo.barogouserapi.config.exception.BusinessException;
import com.barogo.barogouserapi.config.exception.code.StatusCode;
import com.barogo.barogouserapi.domain.rider.dto.RiderRegisterReq;
import com.barogo.barogouserapi.domain.rider.repository.RiderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RiderServiceImpl implements RiderService {

    private final RiderRepository riderRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void register(RiderRegisterReq riderRegisterReq) {
        loginIdDuplicatedCheck(riderRegisterReq.loginId());
        String password = passwordEncoder.encode(riderRegisterReq.password());
        riderRepository.save(riderRegisterReq.toEntity(password));
    }

    private void loginIdDuplicatedCheck(String loginId) {
        riderRepository.findByLoginId(loginId)
                       .ifPresent(rider -> {
                           throw new BusinessException(StatusCode.RIDER_DUPLICATED_LOGIN_ID);
                       });
    }
}
