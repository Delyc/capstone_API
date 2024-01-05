package com.househunting.api.services;

import com.househunting.api.entity.House;

import lombok.Data;

@Data
public class WishlistResponse {


    private Long id;
    private House house; 
}
