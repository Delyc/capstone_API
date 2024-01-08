package com.househunting.api.dto;

import lombok.Data;

@Data
public class AvailabilityResponse {
    private Long id;
    private String startDate;
    private String endDate;

}
