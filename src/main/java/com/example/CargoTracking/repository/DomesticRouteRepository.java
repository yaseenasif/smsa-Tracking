package com.example.CargoTracking.repository;

import com.example.CargoTracking.model.DomesticRoute;
import com.example.CargoTracking.model.InternationalRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DomesticRouteRepository extends JpaRepository<DomesticRoute,Long> {
    List<DomesticRoute> findByOriginAndDestination(String origin, String destination);

    DomesticRoute findByRoute(String routeNumber);

}
