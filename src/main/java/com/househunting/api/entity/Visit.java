package com.househunting.api.entity;

import com.househunting.api.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "agent_id", nullable = false)
    private User agent;

    @ManyToOne
    @JoinColumn(name = "house_id", nullable = false)
    private House house;

    @ManyToOne
    @JoinColumn(name = "availability_id", nullable = false)
    private Availability availability;

    



}

