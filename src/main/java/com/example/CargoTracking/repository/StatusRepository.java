package com.example.CargoTracking.repository;

import com.example.CargoTracking.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {

    List<Status> findByStatus(Boolean aTrue);
}
