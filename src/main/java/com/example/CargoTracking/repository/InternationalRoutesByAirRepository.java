package com.example.CargoTracking.repository;

import com.example.CargoTracking.model.DomesticRoute;
import com.example.CargoTracking.model.InternationalRoutesByAir;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InternationalRoutesByAirRepository extends JpaRepository<InternationalRoutesByAir,Long> {
    List<InternationalRoutesByAir> findByOriginAndDestination(String origin, String destination);

    InternationalRoutesByAir findByRoute(String routeNumber);
}
