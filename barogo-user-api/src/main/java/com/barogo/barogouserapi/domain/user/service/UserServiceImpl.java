package com.barogo.barogouserapi.domain.user.service;

import com.barogo.barogouserapi.config.exception.BusinessException;
import com.barogo.barogouserapi.config.exception.code.StatusCode;
import com.barogo.barogouserapi.config.jwt.JwtService;
import com.barogo.barogouserapi.domain.user.dto.UserCheckLoginIdReq;
import com.barogo.barogouserapi.domain.user.dto.UserLoginReq;
import com.barogo.barogouserapi.domain.user.dto.UserLoginRes;
import com.barogo.barogouserapi.domain.user.dto.UserRegisterReq;
import com.barogo.barogouserapi.domain.user.repository.UserAccessTokenRepository;
import com.barogo.barogouserapi.domain.user.repository.UserRepository;
import com.barogo.model.domain.user.entity.User;
import com.barogo.model.domain.user.entity.UserAccessToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserAccessTokenRepository userAccessTokenRepository;


    @Override
    @Transactional(readOnly = true)
    public void checkLoginId(UserCheckLoginIdReq userCheckLoginIdReq) {
        loginIdDuplicatedCheck(userCheckLoginIdReq.loginId());
    }

    private void loginIdDuplicatedCheck(String loginId) {
        userRepository.findByLoginId(loginId)
                      .ifPresent(user -> {
                          throw new BusinessException(StatusCode.DUPLICATED_LOGIN_ID);
                      });
    }

    @Override
    @Transactional
    /**
     * 1.	회원가입시 필요한 정보는 ID, 비밀번호, 사용자 이름 입니다.
     * 2.	비밀번호는 영어 대문자, 영어 소문자, 숫자, 특수문자 중 3종류 이상으로
     * 		12자리 이상의 문자열로 생성해야 합니다.
     */
    public void register(UserRegisterReq userRegisterReq) {
        loginIdDuplicatedCheck(userRegisterReq.loginId());
        String password = passwordEncoder.encode(userRegisterReq.password());
        userRepository.save(userRegisterReq.toEntity(password));
    }

    @Override
    @Transactional
    /**
     * 전제조건 (2) : 로그인 API 를 구현해 주세요.
     * 1.	사용자로부터 ID, 비밀번호를 입력받아 로그인을 처리합니다.
     * 2.	ID와 비밀번호가 이미 가입되어 있는 회원의 정보와 일치하면 로그인이 되었다는
     * 		응답으로 AccessToken 을 제공합니다.
     */
    public UserLoginRes login(UserLoginReq userLoginReq) {
        User user = userRepository.findByLoginId(userLoginReq.loginId())
            .orElseThrow(() -> new BusinessException(StatusCode.USER_NOT_FOUND));
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                userLoginReq.loginId(),
                userLoginReq.password()
            )
        );
        revokeAllAccessTokens(user);
        saveAccessToken(user, jwtToken, refreshToken);

        return new UserLoginRes(user.getUserId().toString(), user.getLoginId(), jwtToken, refreshToken);
    }

    /**
     * UserAccessToken 테이블에 회원의 토큰 정보 저장
     * @param user 회원
     * @param jwtToken 발급(생성)한 jwt access token
     * @param refreshToken 발급(생성)한 jwt refresh token
     */
    private void saveAccessToken(User user, String jwtToken, String refreshToken) {
        var accessToken = UserAccessToken.builder()
                                         .userSeq(user.getUserSeq())
                                         .token(jwtToken)
                                         .refreshToken(refreshToken)
                                         .revoked(false)
                                         .expired(false)
                                         .build();
        userAccessTokenRepository.save(accessToken);
    }

    private void revokeAllAccessTokens(User user) {
        var validUserTokens = userAccessTokenRepository.findAccessTokensByUserSeq(user.getUserSeq());
        if (validUserTokens.isEmpty())
            return;

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        userAccessTokenRepository.saveAll(validUserTokens);
    }
}
