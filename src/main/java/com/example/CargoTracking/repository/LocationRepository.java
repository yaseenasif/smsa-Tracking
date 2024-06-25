package com.example.CargoTracking.repository;

import com.example.CargoTracking.dto.LocationDto;
import com.example.CargoTracking.model.Country;
import com.example.CargoTracking.model.Facility;
import com.example.CargoTracking.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location , Long> , JpaSpecificationExecutor<Location> {

    @Query("SELECT l FROM Location l WHERE l.status = true")
    List<Location> getActiveLocations();

    @Query("SELECT l FROM Location l WHERE l.status = true AND l.type = :type")
    List<Location> getActiveLocationsByType(String type);

    List<Location> findByLocationName(String Location);

    @Query("SELECT l FROM Location l WHERE l.locationName = :locationName AND l.type =:type And l.status = true")
    Location findByLocationNameAndType(String locationName, String type);
    @Query("SELECT l FROM Location l WHERE l.locationName = :locationName AND l.type =:type And l.status =true")
    Optional<Location> findByLocationNameAndTypeAndFacilityForUser(String locationName, String type);
    Location findByLocationNameAndCountryAndFacility(String locationName, Country country, Facility facility);


}
