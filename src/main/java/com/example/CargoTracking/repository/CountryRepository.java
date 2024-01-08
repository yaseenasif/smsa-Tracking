package com.example.CargoTracking.repository;

import com.example.CargoTracking.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country,Long> {
    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    List<Country> findAllByStatus(Boolean status);

    Optional<Country> findByIdAndStatus(Long id, Boolean status);
}
