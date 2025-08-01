package com.example.CargoTracking.repository;

import com.example.CargoTracking.model.DomesticRoute;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository
public interface DomesticRouteRepository extends JpaRepository<DomesticRoute,Long>, JpaSpecificationExecutor<DomesticRoute> {
    List<DomesticRoute> findByOriginAndDestination(String origin, String destination);

    DomesticRoute findByRoute(String routeNumber);

    List<DomesticRoute> findByRouteIn(Set<String> routeNumbers);

    @Query("SELECT r FROM DomesticRoute r WHERE r.activeStatus = true")
    List<DomesticRoute> getActiveDomesticRoutes();

    List<DomesticRoute> findByOriginAndDestinationAndActiveStatus(String origin, String destination, boolean b);
}
