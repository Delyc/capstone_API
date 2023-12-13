package com.househunting.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.househunting.api.auth.AuthenticationService;
import com.househunting.api.dto.HouseRequest;
import com.househunting.api.entity.House;
import com.househunting.api.services.HouseService;

import lombok.RequiredArgsConstructor;

@RestController
// @RequestMapping("/api/v1/houses")
@RequiredArgsConstructor
public class HouseController {

    @Autowired
    HouseService houseService;

    @Autowired
    AuthenticationService service;

    @PostMapping(value = "/api/v1/houses/create", consumes = "multipart/form-data")
    public ResponseEntity<House> createHouse(@RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("price") String price,
            @RequestParam("googleMapLocation") String googleMapLocation) {
        HouseRequest request = new HouseRequest();
        request.setTitle(title);
        request.setDescription(description);
        request.setPrice(price);
        request.setGoogleMapLocation(googleMapLocation);

        return ResponseEntity.ok(houseService.createHouse(request, file));
    }

    @GetMapping("/api/v1/getAllHouses")
    public ResponseEntity<Object> getAllHouses() {
        return ResponseEntity.ok(houseService.getAllHouses());
    }

    @GetMapping("/api/v1/getAllHouses/{id}")
    public ResponseEntity<Object> getHouseById(@PathVariable Long id) {
        return ResponseEntity.ok(houseService.getHouseById(id));
    }

    @DeleteMapping("/api/v1/deleteHouse/{id}")
    public ResponseEntity<Object> deleteHouseById(@PathVariable Long id) {
        houseService.deleteHouseById(id);
        return ResponseEntity.ok("House deleted successfully");
    }

    @PutMapping(value = "/api/v1/updateHouse/{id}", consumes = "multipart/form-data")
    public ResponseEntity<Object> updateHouse(
            @PathVariable Long id,
            @RequestParam(required = false) MultipartFile file,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String price,
            @RequestParam(required = false) String googleMapLocation) {

        houseService.updateHouse(id, file, title, description, price, googleMapLocation);
        return ResponseEntity.ok("House updated successfully");
    }

    @PostMapping("/api/v1/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody String email) {
        // Logic to generate a unique token and send a password reset link to the user's
        // email
        service.sendPasswordResetEmail(email);
        return ResponseEntity.ok("Password reset instructions sent to your email.");
    }
}
