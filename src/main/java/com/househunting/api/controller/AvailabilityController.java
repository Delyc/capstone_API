package com.househunting.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.househunting.api.dto.AvailabilityResponse;
import com.househunting.api.entity.Availability;
import com.househunting.api.services.AvailabilityService;
import java.util.List;


@RestController
@RequestMapping("/api/v1/availabilities")
public class AvailabilityController {

    @Autowired
    private AvailabilityService availabilityService;


    @GetMapping("/userAvailabilities/{user_id}")
public ResponseEntity<List<AvailabilityResponse>> getAvailabilitiesByUserId(@PathVariable Long user_id) {
    List<AvailabilityResponse> availabilities = availabilityService.getAvailabilitiesByUserId(user_id);
    return new ResponseEntity<>(availabilities, HttpStatus.OK);
}


    @PostMapping("/user/{user_id}")

    public ResponseEntity<Void> saveAvailability(@PathVariable Long user_id, @RequestBody Availability availability) {
        ResponseEntity<Void> response = availabilityService.saveAvailability(user_id, availability);
        return response;
    }

}

