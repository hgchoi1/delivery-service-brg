package com.barogo.barogouserapi.domain.delivery.api;

import static org.springframework.http.ResponseEntity.ok;

import com.barogo.barogouserapi.domain.common.PageRequest;
import com.barogo.barogouserapi.domain.common.ResponseWrapper;
import com.barogo.barogouserapi.domain.delivery.dto.DeliveryListReq;
import com.barogo.barogouserapi.domain.delivery.dto.DeliveryUpdateDestinationReq;
import com.barogo.barogouserapi.domain.delivery.service.DeliveryService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/deliveries")
public class DeliveryController {
  private final DeliveryService deliveryService;

  @GetMapping
  public ResponseEntity<?> list(
      @RequestBody DeliveryListReq deliveryListReq,
      @PageableDefault(size = 100) PageRequest pageReq
  ) {
    Pageable pageable = pageReq.of();
    return ok(ResponseWrapper.of(deliveryService.list(deliveryListReq, pageable)));
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> info(
      @PathVariable("id") UUID id
  ) {
    return ok(ResponseWrapper.of(deliveryService.info(id)));
  }

  @PatchMapping("/{id}/destination")
  public ResponseEntity<?> updateDestination(@RequestBody DeliveryUpdateDestinationReq deliveryUpdateDestinationReq,
      @PathVariable("id") UUID id) {
    deliveryService.updateDestination(id, deliveryUpdateDestinationReq);
    return ok(ResponseWrapper.of("Ok"));
  }

  @PostMapping
  public ResponseEntity<?> createDelivery() {
    deliveryService.createDelivery();
    return ok(ResponseWrapper.of("Ok"));
  }
}
