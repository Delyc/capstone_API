package com.househunting.api.dto;

import java.util.List;

import com.househunting.api.entity.Picture;
import com.househunting.api.entity.Video;

import lombok.Data;

@Data

public class HouseUpdateRequest {
    private String title;
    private String description;
    private String coverImageUrl;
    private String price;
    private String longi;
    private String lat;
    private String streetNumber;
    // Previously googleMapLocation, now using lat and longi
    private List<Picture> pictures;
    private List<Video> videos;
    private List<String> features;
    private Long bedRooms;
    private String typeOfHouse;
    // Getters and setters
}
