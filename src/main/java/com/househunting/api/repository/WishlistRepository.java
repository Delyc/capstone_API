package com.househunting.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.househunting.api.entity.House;
import com.househunting.api.entity.Wishlist;
import com.househunting.api.user.User;

public interface WishlistRepository extends JpaRepository<Wishlist, Long>{

    Wishlist findByUserAndHouse(User user, House house);
    
}
