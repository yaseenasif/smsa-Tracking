package com.example.CargoTracking.repository;

import com.example.CargoTracking.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location , Long> {

    @Query("SELECT l FROM Location l WHERE l.status = true")
    List<Location> getActiveLocations();

    Optional<Location> findByLocationName(String Location);

}
