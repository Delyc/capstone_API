package com.househunting.api.entity;

import com.househunting.api.user.User;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="userID", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name="houseID", referencedColumnName = "id")
    private House house;
    

}
