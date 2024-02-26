package com.househunting.api.entity;

import com.househunting.api.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Availability {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Boolean isBooked = false;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "availability", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("availability")
    private List<Visit> visits;
}
