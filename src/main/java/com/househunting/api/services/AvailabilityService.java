package com.househunting.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.househunting.api.dto.AvailabilityResponse;
import com.househunting.api.dto.HouseResponse;
import com.househunting.api.entity.Availability;
import com.househunting.api.entity.House;
import com.househunting.api.repository.AvailabilityRepository;
import com.househunting.api.user.User;
import com.househunting.api.user.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AvailabilityService {

    @Autowired
    private AvailabilityRepository availabilityRepository;

   public List<AvailabilityResponse> getAvailabilitiesByUserId(Long userId) {
    List<Availability> availabilities = availabilityRepository.findByUserId(userId);
    List<AvailabilityResponse> availabilityResponses = new ArrayList<>();

    for (Availability availability : availabilities) {
        AvailabilityResponse response = new AvailabilityResponse();
        response.setId(availability.getId());
        response.setEndTime(availability.getEndTime());
        response.setStartTime(availability.getStartTime());
        // response.setEndTime(availability.getEndTime());

        // Retrieve house information
        // House house = availability.getHouse();
        // if (house != null) {
        //     HouseResponse houseResponse = new HouseResponse();
        //     houseResponse.setId(house.getId());
        //     houseResponse.setTitle(house.getTitle());
        //     houseResponse.setPrice(house.getPrice());
        //     houseResponse.setCoverImageUrl(house.getCoverImageUrl());
        //     houseResponse.setDescription(house.getDescription());
        //     houseResponse.setGoogleMapLocation(house.getGoogleMapLocation());
        //     // Add other house-related fields as needed

        //     response.setHouse(houseResponse);
        // }

        availabilityResponses.add(response);
    }

    return availabilityResponses;
}

    @Autowired
    UserRepository userRepository;

   public ResponseEntity<Void> saveAvailability(Long user_id, Availability availability) {
        Optional<User> existingUser = userRepository.findById(user_id);

        if (existingUser.isPresent()) {
            User user = existingUser.get();
            availability.setUser(user);
            availabilityRepository.save(availability);

            // return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            // User does not exist, return a 404 Not Found response
            // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return null;
    }

}
