package com.example.CargoTracking.repository;

import com.example.CargoTracking.model.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacilityRepository extends JpaRepository<Facility,Long> {
    List<Facility> getAllByStatus(Boolean status);

    Optional<Facility> findByIdAndStatus(Long id, Boolean status);

//    List<Facility> getAllByStatusAndCountryId(Boolean status, Long country_name);
}
