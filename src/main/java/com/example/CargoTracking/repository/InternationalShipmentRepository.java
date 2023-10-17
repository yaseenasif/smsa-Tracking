package com.example.CargoTracking.repository;

import com.example.CargoTracking.model.DomesticShipment;
import com.example.CargoTracking.model.InternationalShipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InternationalShipmentRepository extends JpaRepository<InternationalShipment,Long> {
    List<InternationalShipment> findByOriginCountryAndCreatedBy(String originCountry, String createdBy);
    List<InternationalShipment> findByDestinationCountryAndCreatedBy(String originCountry, String createdBy);


}
