package com.example.CargoTracking.repository;

import com.example.CargoTracking.dto.InternationalRouteDto;
import com.example.CargoTracking.model.InternationalRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InternationalRouteRepository extends JpaRepository<InternationalRoute,Long> {
    List<InternationalRoute> findByOriginAndDestinationAndType(String origin, String destination, String Type);

    List<InternationalRoute> findAllByType(String type);
}
