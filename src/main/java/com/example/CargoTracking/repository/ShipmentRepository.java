package com.example.CargoTracking.repository;

import com.example.CargoTracking.model.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment , Long> {

}
