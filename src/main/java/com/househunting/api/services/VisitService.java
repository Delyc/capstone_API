package com.househunting.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.househunting.api.dto.VisitRequest;
import com.househunting.api.entity.Availability;
import com.househunting.api.entity.House;
import com.househunting.api.entity.Visit;
import com.househunting.api.repository.AvailabilityRepository;
import com.househunting.api.repository.HouseRepository;
import com.househunting.api.repository.VisitRepository;
import com.househunting.api.user.User;
import com.househunting.api.user.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class VisitService {

    private final VisitRepository visitRepository;
    private final UserRepository userRepository;
    private final AvailabilityRepository availabilityRepository;
    private final HouseRepository houseRepository;

    @Autowired
    public VisitService(VisitRepository visitRepository, UserRepository userRepository, AvailabilityRepository availabilityRepository, HouseRepository houseRepository) {
        this.visitRepository = visitRepository;
        this.userRepository = userRepository;
        this.availabilityRepository = availabilityRepository;
        this.houseRepository = houseRepository;
    }

    public Visit createVisit(VisitRequest visitRequest) {
        // Retrieve user, agent, and availability entities
                System.out.println(visitRequest + " testttt get user #####################################################");
        System.out.println(visitRequest.getAgent_id() + " testttt get agent #####################################################");

        System.out.println(visitRequest.getHouse_id() + " testttt get house #####################################################");   
             System.out.println(visitRequest.getAvailability_id() + " testttt get availability #####################################################");

       
    
  House house = houseRepository.findById(visitRequest.getHouse_id())
            .orElseThrow(() -> new NoSuchElementException("House not found"));
        User agent = userRepository.findById(visitRequest.getAgent_id()).orElseThrow(() -> new NoSuchElementException("Agent not found"));
        Availability availability = availabilityRepository.findById(visitRequest.getAvailability_id()).orElseThrow(() -> new NoSuchElementException("Availability not found"));
   User user = userRepository.findById(visitRequest.getUser_id())
            .orElseThrow(() -> new NoSuchElementException("User not found"));
        // Create and save the Visit entity
        Visit visit = new Visit();
        visit.setUser(user);
        visit.setAgent(agent);
        visit.setHouse(house);
        visit.setAvailability(availability);
        visit.setMessage(visitRequest.getMessage());

        return visitRepository.save(visit);
    }

    public List<Visit> getAllVisits() {
        return visitRepository.findAll();
    }

    public Visit getVisitById(Long id) {
        return visitRepository.findById(id).orElse(null);
    }

    // You can add other methods as needed
}
