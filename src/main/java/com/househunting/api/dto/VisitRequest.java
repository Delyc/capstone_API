package com.househunting.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitRequest {
    private long house_id;    
    private long agent_id;
        private long user_id;
        private long availability_id;
        private String message;





}
