package com.example.CargoTracking.repository;

import com.example.CargoTracking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <User,Long> {

    User findByEmployeeId(String email);
//    @Query("select u.email from User u inner join Location l on u.location = l.id where l.locationName = :location")
//    List<String> findEmailByLocation(@Param("location") String location);

    @Query("SELECT u FROM User u WHERE u.status = true")
    List<User> findUserWithTrueStatus();

    @Query("SELECT u FROM User u WHERE u.status = false")
    List<User> findUserWithFalseStatus();

    Optional<User> findActiveUserById(long id);

}
