package com.househunting.api.dto;

import java.util.Optional;

import com.househunting.api.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HouseRequest {
    private Long id;
    private String title;
    private String description;
    private String coverImageUrl; 
    private String price;
    private String googleMapLocation;
    // private User user;
    private Long userId;
    
}
