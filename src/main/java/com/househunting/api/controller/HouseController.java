package com.househunting.api.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.househunting.api.dto.HouseResponse;
import com.househunting.api.entity.House;
import com.househunting.api.services.HouseService;
import com.househunting.api.user.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@RestController
// @RequestMapping("/api/v1/houses")
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

    @PostMapping(value = "/api/v1/houses/create/{user_id}", consumes = "multipart/form-data")
    public ResponseEntity<House> createHouse(@PathVariable Long user_id,
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("price") String price,
            @RequestParam("googleMapLocation") String googleMapLocation) {
    
        HouseRequest request = new HouseRequest();
        request.setUserId(user_id); 
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
    public ResponseEntity<String> forgotPassword(@RequestBody String email) throws MessagingException, javax.mail.MessagingException {
        service.sendPasswordResetEmail(email);
        return ResponseEntity.ok("Password reset instructions sent to your email.");
    }

     @GetMapping("/api/v1/houses/{user_id}")
    public ResponseEntity<List<HouseResponse>> getHousesForUser(@PathVariable Long user_id) {
        List<HouseResponse> housesForUser = houseService.getHousesForUser(user_id);

        if (!housesForUser.isEmpty()) {
            return new ResponseEntity<>(housesForUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
