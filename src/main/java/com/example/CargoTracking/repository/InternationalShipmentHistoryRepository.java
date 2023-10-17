package com.example.CargoTracking.repository;

import com.example.CargoTracking.model.InternationalShipmentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternationalShipmentHistoryRepository extends JpaRepository<InternationalShipmentHistory, Long> {
}
