package com.example.CargoTracking.repository;

import com.example.CargoTracking.model.ShipmentMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipmentModeRepository extends JpaRepository<ShipmentMode , Long> {


    @Query("SELECT s FROM ShipmentMode s WHERE s.status = true")
    List<ShipmentMode> getActiveShipments();

}
