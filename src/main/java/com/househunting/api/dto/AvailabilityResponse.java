package com.househunting.api.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AvailabilityResponse {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean isBooked=false;

}
