package com.barogo.barogouserapi.domain.rider.service;

import com.barogo.barogouserapi.domain.rider.dto.RiderRegisterReq;

public interface RiderService {
    // 회원가입
    void register(RiderRegisterReq riderRegisterReq);
}
