package com.househunting.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.househunting.api.entity.Visit;

public interface VisitRepository extends JpaRepository<Visit, Long> {
  
}

