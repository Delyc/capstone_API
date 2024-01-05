package com.househunting.api.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.househunting.api.dto.HouseRequest;
import com.househunting.api.entity.House;
import com.househunting.api.entity.Wishlist;
import com.househunting.api.repository.HouseRepository;
import com.househunting.api.repository.WishlistRepository;
import com.househunting.api.user.User;
import com.househunting.api.user.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class WishlistService {

    @Autowired
    WishlistRepository wishlistRepository;

    @Autowired
    HouseRepository houseRepository;

    @Autowired
    UserRepository userRepository;

    public void addHouseToWishlist(Long user_id, Long house_id) {
        User user = userRepository.findById(user_id).orElse(null);

House house = houseRepository.findById(house_id).orElse(null);       

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


    // public void addHouseToWishlist(Long user_id, Long house_id) {
    //     User user = userRepository.findById(user_id).orElse(null);
    //     House house = houseRepository.findById(house_id).orElse(null);

    //     if (user != null && house != null) {
    //         Wishlist existingEntry = wishlistRepository.findByUserAndHouse(user, house);

    //         if (existingEntry == null) {
    //             Wishlist wishlistEntry = new Wishlist();
    //             wishlistEntry.setUser(user);
    //             wishlistEntry.setHouse(house);

    //             wishlistRepository.save(wishlistEntry);
    //         } else {
    //             // Handle case where wishlist entry already exists
    //             // For example: throw new RuntimeException("Wishlist entry already exists");
    //         }
    //     } else {
    //         // Handle cases where user or house is not found
    //         // For example: throw new IllegalArgumentException("User or house not found");
    //     }
    // }

   
    public void removeFromWishlist(Long wishlistItemId) {
        Wishlist wishlistItem = wishlistRepository.findById(wishlistItemId).orElse(null);

        if (wishlistItem != null) {
            House house = wishlistItem.getHouse(); // Get the associated house

            // Remove the wishlist item from the user's wishlist
            User user = wishlistItem.getUser();
            user.getWishlists().remove(wishlistItem);

            // Remove the wishlist item from the house's wishlist
            house.getWishlists().remove(wishlistItem);

            // Remove the item from the repository
            wishlistRepository.delete(wishlistItem);
        }
    }

    @Transactional
    public void emptyWishlistByUserId(Long user_id) {
        wishlistRepository.deleteByUserId(user_id);
    }

    public List<WishlistResponse> getUserWishlistWithHouseDetails(Long user_id) {
        Optional<User> userOptional = userRepository.findById(user_id);
    
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<WishlistResponse> wishlistResponses = new ArrayList<>();
    
            for (Wishlist wishlist : user.getWishlists()) {
                WishlistResponse response = new WishlistResponse();
                response.setId(wishlist.getId());
    
                // Populate HouseResponse without circular references
                House house = wishlist.getHouse();
                // HouseRequest houseResponse = new HouseRequest();                
                House houseResponse = new House();

                houseResponse.setId(house.getId());
                houseResponse.setTitle(house.getTitle());
                // Set other fields as needed
    
                response.setHouse(houseResponse);
                wishlistResponses.add(response);
            }
    
            return wishlistResponses;
        } else {
            return Collections.emptyList();
        }
    }
    
}