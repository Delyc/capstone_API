package com.househunting.api.entity;

import java.util.HashSet;

import com.househunting.api.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long house_id;
    private String title;
    private String description;
    private String coverImageUrl; 
    private String price;
    private String googleMapLocation;
    private String addedBy;
    private boolean addedToWishlist = false;
    @ManyToMany(mappedBy = "wishlist")
    private Set<User> usersWishlist = new HashSet<>();


}
