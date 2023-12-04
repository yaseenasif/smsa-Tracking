package com.example.CargoTracking.repository;

import com.example.CargoTracking.model.Location;
import com.example.CargoTracking.model.OriginEmails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OriginEmailsRepository extends JpaRepository<OriginEmails, Long> {
}
