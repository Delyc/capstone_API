package com.househunting.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WishlistRequest {
    private Long user_id;
    private String recipientEmail;
    private HouseRequest houseRequest;

}
