package com.example.CargoTracking.repository;

import com.example.CargoTracking.model.InternationalShipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternationalShipmentRepository extends JpaRepository<InternationalShipment,Long> {

}
