package com.example.CargoTracking.repository;

import com.example.CargoTracking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository <User,Long> {
    User findByName(String username);
}
