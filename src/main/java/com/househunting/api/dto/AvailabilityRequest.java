package com.househunting.api.dto;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AvailabilityRequest {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
