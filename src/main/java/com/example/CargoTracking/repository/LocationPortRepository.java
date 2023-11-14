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

    @Query("SELECT lp FROM LocationPort lp JOIN lp.location l WHERE l.locationName = :locationName AND lp.status = true")
    List<LocationPort> findLocationPortsByLocationName(String locationName);
}
