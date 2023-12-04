package com.example.CargoTracking.repository;

import com.example.CargoTracking.model.DestinationEmails;
import com.example.CargoTracking.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DestinationEmailsRepository extends JpaRepository<DestinationEmails, Long> {
}
