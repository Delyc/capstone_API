package com.househunting.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.househunting.api.entity.House;
import com.househunting.api.repository.WishlistRepository;
import com.househunting.api.services.WishlistResponse;
import com.househunting.api.services.WishlistService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    @Autowired
    WishlistService wishlistService;

    @Autowired
    WishlistRepository wishlistRepository;
    
    @PostMapping("/addHouse")
    public String addToWishlist(
        @RequestParam("userID") Long userID, 
        @RequestParam("houseID") Long houseID) {
                wishlistService.addHouseToWishlist(userID, houseID);
            return "house added to wishlist successfully";
    }

    @GetMapping("/get/{userID}")
    public ResponseEntity<List<WishlistResponse>> getWishlistForUser(@PathVariable Long userID) {
        List<WishlistResponse> wishlist = wishlistService.getUserWishlistWithHouseDetails(userID);
        return ResponseEntity.ok(wishlist);
    }
}
