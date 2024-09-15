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
        if (properties == null || properties.isEmpty()) {
            return ResponseEntity.noContent().build(); // Returns 204 No Content if no properties are found
        }
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(properties);
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<Property>> getPropertiesByCity(@PathVariable String city) {
        List<Property> properties = propertyService.getPropertiesByCity(city);
        if (properties == null || properties.isEmpty()) {
            return ResponseEntity.noContent().build(); // Returns 204 No Content if no properties are found
        }
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(properties);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<?> addProperty(@RequestBody Property property, @AuthenticationPrincipal UserDetails userDetails) {
        Optional<User> ownerOpt = userService.getUserByUsername(userDetails.getUsername());
        if (ownerOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("User not found");
        }

        User owner = ownerOpt.get();
        property.setOwner(owner);

        try {
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

    private String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private boolean hasAdminRole() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
    }
}
