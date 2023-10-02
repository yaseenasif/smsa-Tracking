package com.example.CargoTracking.repository;

import com.example.CargoTracking.model.ShipmentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentHistoryRepository extends JpaRepository<ShipmentHistory, Long> {
}
