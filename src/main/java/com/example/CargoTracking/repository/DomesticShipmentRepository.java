package com.example.CargoTracking.repository;

import com.example.CargoTracking.model.DomesticShipment;
import com.example.CargoTracking.model.Driver;
import com.example.CargoTracking.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DomesticShipmentRepository extends JpaRepository<DomesticShipment, Long>, JpaSpecificationExecutor<DomesticShipment> {
    Page<DomesticShipment> findByOriginLocation(String origin, Pageable pageable);
    Page<DomesticShipment> findAll( Specification<DomesticShipment> specification, Pageable pageable);

    Page<DomesticShipment> findByDestinationLocation(String destination, Pageable pageable);

    @Query("SELECT d FROM DomesticShipment d WHERE d.createdBy = :user")
    List<DomesticShipment> findAllByCreatedBy(User user);

    @Query("SELECT d FROM DomesticShipment d WHERE d.createdAt = :createdAt")
    List<DomesticShipment> findByCreatedAt(@Param("createdAt") LocalDate createdAt);

    @Query("SELECT d FROM DomesticShipment d WHERE d.activeStatus = :status")
    Page<DomesticShipment> findAllByActiveStatus(Pageable pageable,boolean status);

    @Query("SELECT d FROM DomesticShipment d WHERE d.activeStatus = :status")
    List<DomesticShipment> findAllByActiveStatusMock(boolean status);
}
