package com.househunting.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HouseRequest {
    
    private String title;
    private String description;
    private String coverImageUrl; 
    private String price;
    private String googleMapLocation;
}
