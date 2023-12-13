package com.example.CargoTracking.repository;

import com.example.CargoTracking.model.DomesticShipment;
import com.example.CargoTracking.model.InternationalShipment;
import com.example.CargoTracking.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InternationalShipmentRepository extends JpaRepository<InternationalShipment,Long>, JpaSpecificationExecutor<InternationalShipment> {

    @Query("SELECT i FROM InternationalShipment i WHERE i.originCountry = :originCountry AND i.type = 'by Air'")
    Page<InternationalShipment> findByOriginCountryByAir(String originCountry, Pageable pageable);

    @Query("SELECT i FROM InternationalShipment i WHERE i.originCountry = :originCountry AND i.type = 'by Road'")
    Page<InternationalShipment> findByOriginCountryByRoad(String originCountry, Pageable pageable);

    @Query("SELECT i FROM InternationalShipment i WHERE i.destinationCountry = :destinationCountry AND i.type = 'by Air'")
    Page<InternationalShipment> findByDestinationCountryByAir(String destinationCountry, Pageable pageable);

    @Query("SELECT i FROM InternationalShipment i WHERE i.destinationCountry = :destinationCountry AND i.type = 'by Road'")
    Page<InternationalShipment> findByDestinationCountryByRoad(String destinationCountry, Pageable pageable);

    @Query("SELECT i FROM InternationalShipment i WHERE i.createdBy = :user AND i.type = 'By Air'")
    List<InternationalShipment> findAllByCreatedByForAir(User user);

    @Query("SELECT i FROM InternationalShipment i WHERE i.type = 'By Air' AND i.activeStatus = :activeStatus")
    Page<InternationalShipment> findAllForAir( Pageable pageable,boolean activeStatus);

    @Query("SELECT i FROM InternationalShipment i WHERE i.createdBy = :user AND i.type = 'By Road'")
    List<InternationalShipment> findAllByCreatedByForRoad(User user);

    @Query("SELECT i FROM InternationalShipment i WHERE i.type = 'By Road' AND i.activeStatus = :activeStatus")
    Page<InternationalShipment> findAllForRoad(Pageable pageable,boolean activeStatus);

    @Query("SELECT i FROM InternationalShipment i WHERE i.createdAt = :createdAt")
    List<InternationalShipment> findByCreatedAt(@Param("createdAt") LocalDate createdAt);

    @Query("SELECT i FROM InternationalShipment i WHERE i.createdAt = :now And i.type = :type")
    List<InternationalShipment> findByCreatedAtAndType(LocalDate now, String type);

    InternationalShipment findByPreAlertNumber(String preAlertNumber);
}
