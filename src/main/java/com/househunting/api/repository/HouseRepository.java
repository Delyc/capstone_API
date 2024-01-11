package com.househunting.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.househunting.api.entity.House;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {
    
}
