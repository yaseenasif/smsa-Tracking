package com.example.CargoTracking.repository;

import com.example.CargoTracking.model.LocationPort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationPortRepository extends JpaRepository<LocationPort,Long> {

    @Query("SELECT p FROM LocationPort p WHERE p.status = true")
    List<LocationPort> getActivePorts();
}
