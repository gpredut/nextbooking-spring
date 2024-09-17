package com.nextbooking.server.controller;

import com.nextbooking.server.model.Property;
import com.nextbooking.server.model.User;
import com.nextbooking.server.service.PropertyService;
import com.nextbooking.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Property>> getAllProperties() {
        List<Property> properties = propertyService.getAllProperties();
        if (properties.isEmpty()) {
            return ResponseEntity.noContent().build(); // Returns 204 No Content if no properties are found
        }
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(properties);
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<Property>> getPropertiesByCity(@PathVariable String city) {
        List<Property> properties = propertyService.getPropertiesByCity(city);
        if (properties.isEmpty()) {
            return ResponseEntity.noContent().build(); // Returns 204 No Content if no properties are found
        }
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(properties);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<?> addProperty(@RequestBody Property property, @AuthenticationPrincipal UserDetails userDetails) {
        // Fetch the owner from the username of the currently authenticated user
        Optional<User> ownerOpt = userService.getUserByUsername(userDetails.getUsername());
        if (ownerOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("User not found");
        }

        User owner = ownerOpt.get();
        property.setOwner(owner);

        try {
            // Check if a property with the same address already exists
            Optional<Property> existingProperty = propertyService.getPropertiesByAddress(property.getAddress());
            if (existingProperty.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("Property with the same address already exists");
            }

            Property newProperty = propertyService.addProperty(property);
            if (newProperty == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("An error occurred while adding the property");
            }
            return ResponseEntity.status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(newProperty); // Code 201 Created
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("An error occurred while adding the property");
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('OWNER') and @propertyService.isOwnerOfProperty(#id, authentication.name))")
    public ResponseEntity<?> deleteProperty(@PathVariable Long id) {
        try {
            // Check if the user has permission to delete the property
            if (!propertyService.isOwnerOfProperty(id, getCurrentUsername()) && !hasAdminRole()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("Access denied");
            }

            propertyService.deleteProperty(id);
            return ResponseEntity.noContent().build(); // Code 204 No Content
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("An error occurred while deleting the property");
        }
    }

    @GetMapping("/countByCity")
    public ResponseEntity<List<Long>> countByCity(@RequestParam List<String> cities) {
        List<Long> counts = propertyService.countPropertiesByCities(cities);
        return ResponseEntity.ok(counts);
    }

    @GetMapping("/featured")
    public ResponseEntity<List<Property>> getFeaturedProperties(@RequestParam(defaultValue = "4") int limit) {
        List<Property> properties = propertyService.getFeaturedProperties(limit);
        if (properties.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(properties);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Property>> getProperties(
            @RequestParam String city,
            @RequestParam(defaultValue = "0") Integer min,
            @RequestParam(defaultValue = "999") Integer max) {
        List<Property> properties = propertyService.findProperties(city, min, max);
        return ResponseEntity.ok(properties);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Property> getPropertyById(@PathVariable Long id) {
        Optional<Property> property = propertyService.getPropertyById(id);
        if (property.isPresent()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(property.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(null);
    }

    private String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private boolean hasAdminRole() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
    }
}
