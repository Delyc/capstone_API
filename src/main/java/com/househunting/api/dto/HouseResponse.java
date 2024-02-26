package com.househunting.api.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.househunting.api.entity.Picture;
import com.househunting.api.entity.enums.HouseType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class HouseResponse {
    private Long id;
    private String title;
    private String price;
    private String coverImageUrl;
    private String description;
    private String lat;
    private String longi;
    private String streetNumber;
    private List<WishlistResponse> wishlists;
    private Long agentId;
    private String agentName;
    private Long bedRooms;

    private String agentEmail;
    private String agentPhoneNumber;
// @Enumerated(EnumType.STRING)
    private String typeOfHouse;


    // New fields for picture URLs and video URLs
    private List<String> pictureUrls;
    private List<String> videoUrls;
        private Map<String, Boolean> features = new HashMap<>();

}
