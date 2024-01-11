package com.househunting.api.dto;

import java.util.List;

import lombok.Data;

@Data
public class HouseResponse {
    private Long id;
    private String title;
    private String price;
    private String coverImageUrl;
    private String description;
    private String googleMapLocation;
    private List<WishlistResponse> wishlists;
     private Long agentId; 
    private String agentName;
    private String agentEmail;
    private String agentPhonenumber;

}
