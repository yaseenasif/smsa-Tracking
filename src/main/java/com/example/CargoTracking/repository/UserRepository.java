package com.example.CargoTracking.repository;

import com.example.CargoTracking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository <User,Long> {
    User findByName(String username);
    @Query("select u.email from User u inner join Location l on u.location = l.id where l.locationName = :location")
    List<String> findEmailByLocation(@Param("location") String location);
}
