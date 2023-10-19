package com.example.CargoTracking.repository;

import com.example.CargoTracking.model.DomesticShipment;
import com.example.CargoTracking.model.InternationalShipment;
import com.example.CargoTracking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InternationalShipmentRepository extends JpaRepository<InternationalShipment,Long> {

    @Query("SELECT i FROM InternationalShipment i WHERE i.originCountry = :originCountry AND i.type = 'by Air'")
    List<InternationalShipment> findByOriginCountryByAir(String originCountry);

    @Query("SELECT i FROM InternationalShipment i WHERE i.originCountry = :originCountry AND i.type = 'by Road'")
    List<InternationalShipment> findByOriginCountryByRoad(String originCountry);

    @Query("SELECT i FROM InternationalShipment i WHERE i.destinationCountry = :destinationCountry AND i.type = 'by Air'")
    List<InternationalShipment> findByDestinationCountryByAir(String destinationCountry);

    @Query("SELECT i FROM InternationalShipment i WHERE i.destinationCountry = :destinationCountry AND i.type = 'by Road'")
    List<InternationalShipment> findByDestinationCountryByRoad(String destinationCountry);

    @Query("SELECT i FROM InternationalShipment i WHERE i.createdBy = :user AND i.type = 'by Air'")
    List<InternationalShipment> findAllByCreatedByForAir(User user);

    @Query("SELECT i FROM InternationalShipment i WHERE i.type = 'by Air'")
    List<InternationalShipment> findAllForAir();

    @Query("SELECT i FROM InternationalShipment i WHERE i.createdBy = :user AND i.type = 'by Road'")
    List<InternationalShipment> findAllByCreatedByForRoad(User user);

    @Query("SELECT i FROM InternationalShipment i WHERE i.type = 'by Road'")
    List<InternationalShipment> findAllForRoad();
}
