package com.househunting.api.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.househunting.api.dto.VisitRequest;
import com.househunting.api.entity.Visit;
import com.househunting.api.services.VisitService;

@RestController
@RequestMapping("/api/v1/visits")
public class VisitController {
    
@Autowired
private VisitService visitService;

@PostMapping
public ResponseEntity<Visit> createVisit(
    @RequestParam(name = "user_id") Long user_id,
    // @RequestParam(name = "agent_id") Long agent_id,
    @RequestParam(name = "house_id") Long house_id,
    @RequestParam(name = "availability_id") Long availability_id,
    
        @RequestBody Map<String, String> requestBody
) {
    System.out.println("userId: " + user_id);
    // System.out.println("agentId: " + agent_id);
    System.out.println("houseId: " + house_id);
    System.out.println("availabilityId: " + availability_id);
    String message = requestBody.get("message");

    VisitRequest visitRequest = new VisitRequest(user_id, house_id, availability_id, message);
    Visit createdVisit = visitService.createVisit(visitRequest);
    return new ResponseEntity<>(createdVisit, HttpStatus.CREATED);

    }
}
