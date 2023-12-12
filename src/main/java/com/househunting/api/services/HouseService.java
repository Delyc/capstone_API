package com.househunting.api.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.househunting.api.entity.House;
import com.househunting.api.repository.HouseRepository;
import org.springframework.stereotype.Service;

@Service
public class HouseService {

    @Autowired HouseRepository houseRepository;

    public House createHouse(House house) {
        return houseRepository.save(house);
    }
    

}
