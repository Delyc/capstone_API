package com.househunting.api.dto;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.househunting.api.entity.enums.HouseType;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
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
    private String lat;
    private String longi;
    private String streetNumber;
    private Long userId;
    private Long bedRooms;
// @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // @Column(nullable = false)
    private LocalDateTime updatedAt;
    // @Enumerated(EnumType.STRING)

    private String typeOfHouse;
    // New fields for picture and video URLs
    private List<String> pictureUrls;
    private List<String> videoUrls;

    private Map<String, Boolean> features = new HashMap<>();
}
