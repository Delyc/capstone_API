package com.househunting.api.dto;

import java.util.List;
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
    private Long userId;

    // New fields for picture and video URLs
    private List<String> pictureUrls;
    private List<String> videoUrls;
}
