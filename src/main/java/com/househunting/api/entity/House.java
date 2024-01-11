package com.househunting.api.entity;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.househunting.api.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties("houses") // Optional: ignore the 'houses' property in User to avoid circular reference
    private User agent;

       @OneToMany(mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval = true)

    // @JoinColumn(name = "house_id", referencedColumnName = "id")

    @JsonIgnoreProperties("house")
    private List<Wishlist> wishlists;

    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("house")
    private List<Visit> visits;
    // @OneToMany(mappedBy = "house")
    // private List<Wishlist> wishlists;
    private String title;
    private String description;
    private String coverImageUrl;
    private String price;
    private String googleMapLocation;

}
