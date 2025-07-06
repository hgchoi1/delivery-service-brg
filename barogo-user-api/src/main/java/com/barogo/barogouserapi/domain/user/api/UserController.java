package com.barogo.barogouserapi.domain.user.api;

import static org.springframework.http.ResponseEntity.ok;

import com.barogo.barogouserapi.domain.common.ResponseWrapper;
import com.barogo.barogouserapi.domain.user.dto.UserCheckLoginIdReq;
import com.barogo.barogouserapi.domain.user.dto.UserLoginReq;
import com.barogo.barogouserapi.domain.user.dto.UserRegisterReq;
import com.barogo.barogouserapi.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

  private final UserService userService;


  @GetMapping("/check-login-id")
  public ResponseEntity<?> checkId(@ModelAttribute UserCheckLoginIdReq request) {
    userService.checkLoginId(request);
    return ok(ResponseWrapper.of("Ok"));
  }

  @PostMapping
  public ResponseEntity<?> register(@RequestBody UserRegisterReq request) {
    userService.register(request);
    return ok(ResponseWrapper.of("Ok"));
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(
      @RequestBody UserLoginReq userLoginReq
  ) {
    return ok(ResponseWrapper.of(userService.login(userLoginReq)));
  }

}
