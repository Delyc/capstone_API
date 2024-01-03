package com.househunting.api.services;

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

    // public List<House> getWishlistForUser(User user) {
    //     List<Wishlist> wishlistItems = wishlistRepository.findByUser(user);
    //     return wishlistItems.stream().map(Wishlist::getHouse).collect(Collectors.toList());
    // }
}

