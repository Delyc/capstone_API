package com.househunting.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.househunting.api.dto.HouseRequest;
import com.househunting.api.entity.House;
import com.househunting.api.services.HouseService;

import lombok.RequiredArgsConstructor;


@RestController
// @RequestMapping("/api/v1/houses")
@RequiredArgsConstructor
public class HouseController {
     
    
    @Autowired HouseService houseService;
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


    
   


         
    
}


