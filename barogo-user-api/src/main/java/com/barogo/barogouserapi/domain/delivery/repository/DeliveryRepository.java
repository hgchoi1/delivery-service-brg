package com.barogo.barogouserapi.domain.delivery.repository;

import com.barogo.model.domain.delivery.entity.Delivery;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long>, QDeliveryRepository {

  Optional<Delivery> findByDeliveryId(UUID deliveryId);

}
