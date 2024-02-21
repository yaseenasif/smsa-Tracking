package com.example.CargoTracking.repository;

import com.amazonaws.services.ec2.model.Route;
import com.example.CargoTracking.model.DomesticRoute;
import com.example.CargoTracking.model.DomesticShipment;
import com.example.CargoTracking.model.InternationalRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DomesticRouteRepository extends JpaRepository<DomesticRoute,Long>, JpaSpecificationExecutor<DomesticRoute> {
    List<DomesticRoute> findByOriginAndDestination(String origin, String destination);

    DomesticRoute findByRouteNumber(String routeNumber);

    @Query("SELECT r FROM DomesticRoute r WHERE r.activeStatus = true")
    List<DomesticRoute> getActiveDomesticRoutes();

    List<DomesticRoute> findByOriginAndDestinationAndActiveStatus(String origin, String destination, boolean b);
}
