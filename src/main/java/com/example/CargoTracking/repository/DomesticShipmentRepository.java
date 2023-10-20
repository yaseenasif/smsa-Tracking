package com.example.CargoTracking.repository;

import com.example.CargoTracking.model.DomesticShipment;
import com.example.CargoTracking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DomesticShipmentRepository extends JpaRepository<DomesticShipment, Long> {
    List<DomesticShipment> findByOriginLocation(String origin);
    List<DomesticShipment> findByDestinationLocation(String destination);

    @Query("SELECT d FROM DomesticShipment d WHERE d.createdBy = :user")
    List<DomesticShipment> findAllByCreatedBy(User user);
}
