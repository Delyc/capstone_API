package com.househunting.api.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.househunting.api.entity.Availability;
import com.househunting.api.repository.AvailabilityRepository;

import java.util.List;

@Service
public class AvailabilityService {

    @Autowired
    private AvailabilityRepository availabilityRepository;

    public List<Availability> getAvailabilitiesByUserId(Long userId) {
        return availabilityRepository.findByUserId(userId);
    }

    public void saveAvailability(Availability availability) {
        availabilityRepository.save(availability);
    }

}
