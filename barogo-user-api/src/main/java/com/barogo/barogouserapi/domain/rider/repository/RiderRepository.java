package com.barogo.barogouserapi.domain.rider.repository;

import com.barogo.model.domain.rider.entity.Rider;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RiderRepository extends JpaRepository<Rider, Long> {
  Optional<Rider> findByLoginId(String loginId);
}
