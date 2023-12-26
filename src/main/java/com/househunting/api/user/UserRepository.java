package com.househunting.api.user;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.househunting.api.entity.House;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);


    
}
