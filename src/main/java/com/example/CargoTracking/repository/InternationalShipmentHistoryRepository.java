package com.example.CargoTracking.repository;

import com.example.CargoTracking.model.DomesticShipmentHistory;
import com.example.CargoTracking.model.InternationalShipmentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InternationalShipmentHistoryRepository extends JpaRepository<InternationalShipmentHistory, Long> {
    List<InternationalShipmentHistory> findByInternationalShipmentId(Long id);

}
