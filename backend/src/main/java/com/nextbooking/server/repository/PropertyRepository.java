package com.nextbooking.server.repository;

import com.nextbooking.server.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    // Method to find a property by its address
    Optional<Property> findByAddress(String address);

    // Method to find properties by city
    List<Property> findByCity(String city);

    // Method to count properties by city
    @Query("SELECT COUNT(p) FROM Property p WHERE LOWER(p.city) = LOWER(:city)")
    Long countByCity(@Param("city") String city);
}
