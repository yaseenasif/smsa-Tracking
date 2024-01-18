package com.example.CargoTracking.repository;

import com.example.CargoTracking.model.User;
import com.example.CargoTracking.model.User1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface User1Repository  extends JpaRepository<User,Long> {
    User1 findByEmail(String email);

    User1 save(User1 user);
}
