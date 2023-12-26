package com.househunting.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.househunting.api.entity.House;
import com.househunting.api.user.User;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {

    Object findByAddedBy(String email);
    // House findByHouseId(Long house_id);



}
