package com.example.CargoTracking.repository;

import com.example.CargoTracking.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleTypeRepository extends JpaRepository<Vehicle,Long> {

    List<Vehicle> findByStatusTrue();

    Vehicle findByName(String name);

}
