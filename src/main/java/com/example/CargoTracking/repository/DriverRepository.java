package com.example.CargoTracking.repository;

import com.example.CargoTracking.model.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long>, JpaSpecificationExecutor<Driver> {
//    @Query("SELECT d FROM Driver d WHERE d.status = true")
//    Page<Driver> getActiveLocations(Specification<Driver> specification, Pageable pageable);
//    @Query("SELECT d FROM Driver d WHERE d.status = true")
//    Page<Driver> getActiveLocations(Specification<Driver> specification, Pageable pageable);
//    Page<Driver> findByStatusIsTrue(Pageable pageable);
//    Page<Driver> findByStatusIsTrue(Specification<Driver> driver,Pageable pageable);


}
