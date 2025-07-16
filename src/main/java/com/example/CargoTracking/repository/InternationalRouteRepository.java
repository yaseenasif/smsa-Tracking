package com.example.CargoTracking.repository;

import com.example.CargoTracking.dto.InternationalRouteDto;
import com.example.CargoTracking.model.InternationalRoute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InternationalRouteRepository extends JpaRepository<InternationalRoute,Long> {
   // List<InternationalRoute> findByOriginAndDestinationAndTypeAndStatus(String origin, String destination, String Type,boolean status);

    @Query("SELECT r FROM InternationalRoute r WHERE r.origin = :origin AND r.destination = :destination AND r.type = :type AND r.status = :status")
    List<InternationalRoute> findByOriginAndDestinationAndTypeAndStatus(@Param("origin") String origin,
                                        @Param("destination") String destination,
                                        @Param("type") String type,
                                        @Param("status") boolean status);
    List<InternationalRoute> findAllByTypeAndStatus(String air,boolean status);

    Page<InternationalRoute> findAll(Specification<InternationalRoute> specification, Pageable pageable);
}
