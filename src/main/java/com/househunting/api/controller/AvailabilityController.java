package com.househunting.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.househunting.api.dto.AvailabilityResponse;
import com.househunting.api.dto.AvailabilityUpdateRequest;
import com.househunting.api.entity.Availability;
import com.househunting.api.services.AvailabilityService;

import java.time.LocalDateTime;
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

    @PutMapping("/{id}")
     public ResponseEntity<AvailabilityResponse> updateAvailability(
            @PathVariable Long id,
            @RequestBody AvailabilityUpdateRequest request) {

        LocalDateTime startTime = request.getStartTime();
        LocalDateTime endTime = request.getEndTime();
        Boolean isBooked = request.getIsBooked();

        Availability updatedAvailability = availabilityService.updateAvailability(id, startTime, endTime, isBooked);

        // Map the updated availability to a response DTO 
        AvailabilityResponse response = mapToResponse(updatedAvailability);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private AvailabilityResponse mapToResponse(Availability availability) {
        AvailabilityResponse response = new AvailabilityResponse();
        // Map fields accordingly
        response.setId(availability.getId());
        response.setStartTime(availability.getStartTime());
        response.setEndTime(availability.getEndTime());
        response.setIsBooked(availability.getIsBooked());
        return response;
    }

    @DeleteMapping("/availability/{id}")
public ResponseEntity<Void> deleteAvailability(@PathVariable Long id) {
    availabilityService.deleteAvailability(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
}

@GetMapping("/all")
    public ResponseEntity<List<AvailabilityResponse>> getAllAvailabilities() {
        List<AvailabilityResponse> availabilities = availabilityService.getAllAvailabilities();
        return new ResponseEntity<>(availabilities, HttpStatus.OK);
    }


}

