package com.example.CargoTracking.repository;

import com.example.CargoTracking.model.EmailAddressForRoutes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailAddressForRouteRepository extends JpaRepository<EmailAddressForRoutes,Long> {
  EmailAddressForRoutes findByType(String type);
}
