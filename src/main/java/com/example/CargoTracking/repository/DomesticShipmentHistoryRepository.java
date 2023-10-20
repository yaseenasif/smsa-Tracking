package com.example.CargoTracking.repository;

import com.example.CargoTracking.model.DomesticShipmentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DomesticShipmentHistoryRepository extends JpaRepository<DomesticShipmentHistory, Long> {
}
