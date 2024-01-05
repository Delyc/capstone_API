package com.househunting.api.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

       @OneToMany(mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval = true)

    // @JoinColumn(name = "house_id", referencedColumnName = "id")

    @JsonIgnoreProperties("house")
    private List<Wishlist> wishlists;
    // @OneToMany(mappedBy = "house")
    // private List<Wishlist> wishlists;
    private String title;
    private String description;
    private String coverImageUrl;
    private String price;
    private String googleMapLocation;

}
