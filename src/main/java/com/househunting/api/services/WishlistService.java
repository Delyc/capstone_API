package com.househunting.api.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.househunting.api.entity.House;
import com.househunting.api.entity.Wishlist;
import com.househunting.api.repository.HouseRepository;
import com.househunting.api.repository.WishlistRepository;
import com.househunting.api.user.User;
import com.househunting.api.user.UserRepository;

@Service
public class WishlistService {

    @Autowired
    WishlistRepository wishlistRepository;

    @Autowired
    HouseRepository houseRepository;

    @Autowired
    UserRepository userRepository;

    public void addHouseToWishlist(Long userID, Long houseID) {
               User user = userRepository.findById(userID).orElse(null);

       House house = houseRepository.findById(houseID).orElse(null);       

       if (user != null && house != null) {
            Wishlist existingWishlistItem = wishlistRepository.findByUserAndHouse(user, house);

            if (existingWishlistItem != null) {
                // Item already exists, remove it
                wishlistRepository.delete(existingWishlistItem);
            } else {
                // Item doesn't exist, add it
                Wishlist wishlist = new Wishlist();
                wishlist.setUser(user);
                wishlist.setHouse(house);
                wishlistRepository.save(wishlist);
            }
        }

    }

    public List<WishlistResponse> getUserWishlistWithHouseDetails(Long userID) {
                        // System.out.println( userID + "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

        Optional<User> userOptional = userRepository.findById(userID);
                                    System.out.println(userOptional + "user optionsl $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

        if (userOptional.isPresent()) {
            User user = userOptional.get();
                            System.out.println(user + "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

            List<WishlistResponse> wishlistResponses = new ArrayList<>();

            for (Wishlist wishlist : user.getWishlists()) {
                WishlistResponse response = new WishlistResponse();
                response.setId(wishlist.getId());

                // Fetch house details from the wishlist entry directly
                House house = wishlist.getHouse();
                // Assuming you have a separate WishlistResponse class with setId() and
                // setHouse() methods

                System.out.println(house + "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
                response.setHouse(house);

                wishlistResponses.add(response);
            }

            return wishlistResponses;
        } else {
            // Handle user not found scenario, maybe throw an exception or return an empty
            // list
            return Collections.emptyList();
        }
    }
    // public List<House> getWishlistForUser(User user) {
    //     List<Wishlist> wishlistItems = wishlistRepository.findByUser(user);
    //     return wishlistItems.stream().map(Wishlist::getHouse).collect(Collectors.toList());
    // }
}

