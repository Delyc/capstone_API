package com.househunting.api.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.househunting.api.entity.Availability;

import java.util.List;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    List<Availability> findByUserId(Long userId);
}

