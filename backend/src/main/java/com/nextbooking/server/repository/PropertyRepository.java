package com.nextbooking.server.repository;

import com.nextbooking.server.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    // Find a property by its address
    Optional<Property> findByAddress(String address);

    // Find properties by city
    List<Property> findByCity(String city);

    // Count properties by city
    @Query("SELECT COUNT(p) FROM Property p WHERE LOWER(p.city) = LOWER(:city)")
    Long countByCity(@Param("city") String city);

    // Custom query for featured properties with pagination
    @Query("SELECT p FROM Property p WHERE p.featured = true")
    List<Property> findFeaturedProperties(Pageable pageable);

    // Find properties by city, minPrice and maxPrice
    List<Property> findByCityAndCheapestPriceBetween(String city, Integer minPrice, Integer maxPrice);
}
