package com.househunting.api.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.househunting.api.dto.HouseRequest;
import com.househunting.api.entity.House;
import com.househunting.api.repository.HouseRepository;

@Service
public class HouseService {

    @Autowired 
    Cloudinary cloudinary;
    @Autowired 
    HouseRepository houseRepository;
    public House createHouse(HouseRequest request , MultipartFile file) {
        try{
            var uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String coverImageUrl = (String) uploadResult.get("secure_url");

            var house = House.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .coverImageUrl(coverImageUrl)
                .price(request.getPrice())
                .googleMapLocation(request.getGoogleMapLocation())
                .build();

            houseRepository.save(house);
            return house;
        } catch (Exception e) {
            e.printStackTrace(); 
            throw new RuntimeException("Failed to create house with cover image");
        }

      
    }

    public Object getAllHouses() {
        return houseRepository.findAll();
    }
    
   

}
