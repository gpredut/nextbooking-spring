package com.nextbooking.server.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "properties")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String distance;  //distance from the city center

    @ElementCollection
    @CollectionTable(name = "property_photos", joinColumns = @JoinColumn(name = "property_id"))
    @Column(name = "photo_url")
    private List<String> photos;

    @Column(length = 2000)
    private String desc;

    @Column(nullable = false)
    private Double cheapestPrice;

    @Column(nullable = false)
    private boolean featured;

    @ElementCollection
    @CollectionTable(name = "property_rooms", joinColumns = @JoinColumn(name = "property_id"))
    @Column(name = "room_details")
    private List<String> rooms;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

}

