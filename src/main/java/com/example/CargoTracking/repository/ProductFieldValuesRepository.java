package com.example.CargoTracking.repository;

import com.example.CargoTracking.model.ProductFieldValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductFieldValuesRepository extends JpaRepository<ProductFieldValues,Long> {
}
