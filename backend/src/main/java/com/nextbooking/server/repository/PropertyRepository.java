package com.nextbooking.server.repository;

import com.nextbooking.server.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    // Find properties by city
    List<Property> findByCity(String city);
}
