package com.barogo.barogouserapi.user;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.barogo.barogouserapi.config.exception.BusinessException;
import com.barogo.barogouserapi.config.exception.CustomHttpMessageNotReadableException;
import com.barogo.barogouserapi.config.exception.code.StatusCode;
import com.barogo.barogouserapi.domain.user.dto.UserLoginReq;
import com.barogo.barogouserapi.domain.user.dto.UserRegisterReq;
import com.barogo.barogouserapi.domain.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserTest {

  @Autowired UserService userService;
  @Autowired UserDetailsService userDetails;

  @Test
  void register_DTO_테스트() {

    // 1. 실패 -아이디 미입력
    CustomHttpMessageNotReadableException ex1 = assertThrows(CustomHttpMessageNotReadableException.class, () -> {
      new UserRegisterReq(
          "홍길녀",
          "01098765432",
          null,
          "honggil12"
      );
    });
    assertEquals(StatusCode.INVALID_INPUT, ex1.getStatusCode());

    // 2. 실패 -비밀번호 미입력
    CustomHttpMessageNotReadableException ex2 = assertThrows(CustomHttpMessageNotReadableException.class, () -> {
      new UserRegisterReq(
          "홍길녀",
          "01098765432",
          "guesthong",
          null
      );
    });
    assertEquals(StatusCode.INVALID_INPUT, ex2.getStatusCode());

    // 3. 실패 -이름 미입력
    CustomHttpMessageNotReadableException ex3= assertThrows(CustomHttpMessageNotReadableException.class, () -> {
      new UserRegisterReq(
          null,
          "01098765432",
          "guesthong",
          "honggil12"
      );
    });
    assertEquals(StatusCode.INVALID_INPUT, ex3.getStatusCode());

    // 4. 실패 -비밀번호 조건 만족못함
    CustomHttpMessageNotReadableException ex4 = assertThrows(CustomHttpMessageNotReadableException.class, () -> {
      new UserRegisterReq(
          "홍길녀",
          "01098765432",
          "guesthong",
          "honggil12"
      );
    });
    assertEquals(StatusCode.PASSWORD_INVALID_FORMAT, ex4.getStatusCode());

    // 5. 성공
    assertDoesNotThrow(()-> {
      new UserRegisterReq(
          "홍길녀",
          "01098765432",
          "guesthong",
          "Honggil12345"
      );
    });
  }

  @Test
  void register_테스트() {

    // 1. 실패 -아이디 중복
    assertThrows(BusinessException.class, () ->
        userService.register(
            new UserRegisterReq(
                "김아무개",
                "01055556666",
                "abcdefgh",
                "abcdefgh1234!!"
            )
        )
    );

    // 2. 성공
    assertDoesNotThrow(() -> {
      userService.register(new UserRegisterReq(
          "김아무개",
          "01055556666",
          "abcdefgh",
          "Abcdefgh1234!!"
      ));
    });

  }

  @Test
  void login_테스트() {
    // 준비 회원가입
    userService.register(
        new UserRegisterReq(
            "박아무개",
            "01066667777",
            "abcde",
            "abcde1234567!!"
        )
    );

    // 1. 실패 -비밀번호 틀림
    assertThrows(BusinessException.class, () -> {
      userService.login(new UserLoginReq("abcde", "abcde0000000!!"));
    });

    // 2. 실패 -계정 없음
    assertThrows(BusinessException.class, () -> {
      userService.login(new UserLoginReq("qwert", "abcde1234567!!"));
    });

    // 2. 실패 -계정 없음
    assertThrows(BusinessException.class, () -> {
      userService.login(new UserLoginReq("qwert", "abcde1234567!!"));
    });

    // 4. 성공
    assertDoesNotThrow(() -> {
      userService.login(new UserLoginReq("abcde", "abcde1234567!!"));
    });
  }

}
