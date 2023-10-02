package com.example.CargoTracking.repository;

import com.example.CargoTracking.model.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleTypeRepository extends JpaRepository<VehicleType,Long> {

    @Query("SELECT v FROM VehicleType v WHERE v.status = true")
    List<VehicleType> getActiveVehicles();

}
