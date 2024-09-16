package com.nextbooking.server.repository;

import com.nextbooking.server.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    // Find a property by its address
    Optional<Property> findByAddress(String address);

    // Find properties by city
    List<Property> findByCity(String city);

    // Count properties by city
    @Query("SELECT COUNT(p) FROM Property p WHERE LOWER(p.city) = LOWER(:city)")
    Long countByCity(@Param("city") String city);

    // Custom query for featured properties with a limit
    @Query("SELECT p FROM Property p WHERE p.featured = true")
    List<Property> findFeaturedProperties(@Param("limit") int limit);

}
