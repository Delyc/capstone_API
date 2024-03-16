package com.househunting.api.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.househunting.api.auth.AuthenticationService;
import com.househunting.api.dto.HouseRequest;
import com.househunting.api.dto.HouseResponse;
import com.househunting.api.dto.HouseUpdateRequest;
import com.househunting.api.entity.House;
import com.househunting.api.services.HouseService;
import com.househunting.api.user.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "http://localhost:3000")

@RequestMapping("/api/v1/houses")
@RequiredArgsConstructor

public class HouseController {

    @Autowired
    HouseService houseService;

    @Autowired
    AuthenticationService service;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public Object Welcome() {
        return "Welcome to House Hunting API";
    }

    @PostMapping("/api/v1/houses/create/{user_id}")
    public ResponseEntity<House> createHouse(@PathVariable("user_id") Long userId,
                                             @RequestBody HouseResponse createHouseDto) {
        HouseRequest request = new HouseRequest();

        request.setUserId(userId);
        request.setTitle(createHouseDto.getTitle());
        request.setDescription(createHouseDto.getDescription());
        request.setCoverImageUrl(createHouseDto.getCoverImageUrl());
        request.setPrice(createHouseDto.getPrice());
        request.setLongi(createHouseDto.getLongi());
        request.setLat(createHouseDto.getLat());
        request.setStreetNumber(createHouseDto.getStreetNumber());
        // request.setGoogleMapLocation(createHouseDto.getGoogleMapLocation());
        request.setPictureUrls(createHouseDto.getPictureUrls());
        request.setVideoUrls(createHouseDto.getVideoUrls());
        request.setFeatures(createHouseDto.getFeatures());
request.setBedRooms(createHouseDto.getBedRooms());
request.setTypeOfHouse(createHouseDto.getTypeOfHouse());
        House createdHouse = houseService.createHouse(request);
        return ResponseEntity.ok(createdHouse);
    }
    

    @GetMapping("/getAllHouses")
    public ResponseEntity<Object> getAllHouses() {
        return ResponseEntity.ok(houseService.getAllHouses());
    }

    @GetMapping("/getAllHouses/{id}")
    public ResponseEntity<Object> getHouseById(@PathVariable Long id) {
        return ResponseEntity.ok(houseService.getHouseById(id));
    }

    @DeleteMapping("/api/v1/deleteHouse/{id}")
    public ResponseEntity<Object> deleteHouseById(@PathVariable Long id) {
        houseService.deleteHouseById(id);
        return ResponseEntity.ok("House deleted successfully");
    }

    @PutMapping("/updateHouse/{id}")
    public ResponseEntity<Object> updateHouse(@PathVariable Long id, @RequestBody HouseUpdateRequest updateHouseDto) {
        houseService.updateHouse(id, updateHouseDto);
        return ResponseEntity.ok("House updated successfully");
    }
    
    @PostMapping("/api/v1/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody String email) throws MessagingException, javax.mail.MessagingException {
        service.sendPasswordResetEmail(email);
        return ResponseEntity.ok("Password reset instructions sent to your email.");
    }

     @GetMapping("/{user_id}")
    public ResponseEntity<List<HouseResponse>> getHousesForUser(@PathVariable Long user_id) {
        List<HouseResponse> housesForUser = houseService.getHousesForUser(user_id);

        if (!housesForUser.isEmpty()) {
            return new ResponseEntity<>(housesForUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
