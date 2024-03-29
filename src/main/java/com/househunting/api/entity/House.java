package com.househunting.api.entity;

import com.househunting.api.entity.enums.HouseType;
import com.househunting.api.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "agent_id", nullable = false)
    @JsonIgnoreProperties("houses")
    private User agent;

    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("house")
    private List<Wishlist> wishlists;

    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("house")
    private List<Visit> visits;

    // New fields for pictures and videos
    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("house")
    private List<Picture> pictures;

    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("house")
    private List<Video> videos;

    private String title;
    private String description;
    private String coverImageUrl;
    private String price;
    private String lat;
    private String longi;
    private String streetNumber;
    private Long bedRooms;
    private Long bathRooms;
    private String liveLocation;
    private Boolean availableStatus=true;
    // @Enumerated(EnumType.STRING)
    private String typeOfHouse;
  @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

        @ElementCollection
    @CollectionTable(name = "house_features", joinColumns = @JoinColumn(name = "house_id"))
    @MapKeyColumn(name = "feature_name")
    @Column(name = "is_available")
    private Map<String, Boolean> features = new HashMap<>();
}
