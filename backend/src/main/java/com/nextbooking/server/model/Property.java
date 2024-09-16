package com.nextbooking.server.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "properties", uniqueConstraints = @UniqueConstraint(columnNames = "address"))
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

    @Column(nullable = false, unique = true)
    private String address;

    @Column(nullable = false)
    private Double distance;

    @ElementCollection
    @CollectionTable(name = "property_photos", joinColumns = @JoinColumn(name = "property_id"))
    @Column(name = "photo_url")
    private List<String> photos;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false)
    private Integer cheapestPrice;

    @Column(nullable = false)
    private boolean featured;

    @ElementCollection
    @CollectionTable(name = "property_rooms", joinColumns = @JoinColumn(name = "property_id"))
    @Column(name = "room_details")
    private List<String> rooms;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    @JsonBackReference
    private User owner;
}
