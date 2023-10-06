package com.example.CargoTracking.repository;

import com.example.CargoTracking.model.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment , Long> {
    List<Shipment> findByOriginCountryAndCreatedBy(String originCountry,String createdBy);
    List<Shipment> findByDestinationCountryAndCreatedBy(String destination,String createdBy);
}
