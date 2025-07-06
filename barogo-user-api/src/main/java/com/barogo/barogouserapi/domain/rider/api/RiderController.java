package com.barogo.barogouserapi.domain.rider.api;

import static org.springframework.http.ResponseEntity.ok;

import com.barogo.barogouserapi.domain.common.ResponseWrapper;
import com.barogo.barogouserapi.domain.rider.dto.RiderRegisterReq;
import com.barogo.barogouserapi.domain.rider.service.RiderService;
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
@RequestMapping("/api/v1/riders")
public class RiderController {

  private final RiderService riderService;

  @PostMapping
  public ResponseEntity<?> register(@RequestBody RiderRegisterReq riderRegisterReq) {
    riderService.register(riderRegisterReq);
    return ok(ResponseWrapper.of("Ok"));
  }

}
