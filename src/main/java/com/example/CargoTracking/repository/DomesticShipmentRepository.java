package com.example.CargoTracking.repository;

import com.example.CargoTracking.model.DomesticShipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DomesticShipmentRepository extends JpaRepository<DomesticShipment, Long> {
    List<DomesticShipment> findByOriginCountryAndCreatedBy(String originCountry, String createdBy);
    List<DomesticShipment> findByDestinationCountryAndCreatedBy(String destination, String createdBy);
}
