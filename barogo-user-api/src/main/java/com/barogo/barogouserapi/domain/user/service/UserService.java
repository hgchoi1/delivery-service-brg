package com.barogo.barogouserapi.domain.user.service;

import com.barogo.barogouserapi.domain.user.dto.UserCheckLoginIdReq;
import com.barogo.barogouserapi.domain.user.dto.UserLoginReq;
import com.barogo.barogouserapi.domain.user.dto.UserLoginRes;
import com.barogo.barogouserapi.domain.user.dto.UserRegisterReq;

public interface UserService {
    // 중복체크
    void checkLoginId(UserCheckLoginIdReq userCheckLoginIdReq);
    // 회원가입
    void register(UserRegisterReq userRegisterReq);
    // 로그인
    UserLoginRes login(UserLoginReq userLoginReq);
}
