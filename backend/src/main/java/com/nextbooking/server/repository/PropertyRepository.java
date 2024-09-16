package com.nextbooking.server.repository;

import com.nextbooking.server.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    // Method to find a property by its address
    Optional<Property> findByAddress(String address);

    // Method to find properties by city
    List<Property> findByCity(String city);
}
