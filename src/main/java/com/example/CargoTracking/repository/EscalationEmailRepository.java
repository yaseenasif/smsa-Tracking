package com.example.CargoTracking.repository;

import com.example.CargoTracking.model.EscalationEmails;
import com.example.CargoTracking.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EscalationEmailRepository extends JpaRepository<EscalationEmails, Long> {
}
