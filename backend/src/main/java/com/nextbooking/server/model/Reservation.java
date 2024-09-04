package com.nextbooking.server.model;

import jakarta.persistence.*;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "guest_id") // This should match the column in your database
    private User guest;

    // other fields, getters, and setters

    // Constructor
    public Reservation(User guest) {
        this.guest = guest;
    }

    public Reservation() {
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getGuest() {
        return guest;
    }

    public void setUser(User guest) {
        this.guest = guest;
    }
}
