package com.nextbooking.server.service;

import com.nextbooking.server.model.Property;
import com.nextbooking.server.model.User;
import com.nextbooking.server.repository.PropertyRepository;
import com.nextbooking.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    public List<Property> getPropertiesByCity(String city) {
        return propertyRepository.findByCity(city);
    }

    public Property addProperty(Property property) {
        return propertyRepository.save(property);
    }

    public void deleteProperty(Long id) {
        // Check if the property exists before attempting to delete
        if (!propertyRepository.existsById(id)) {
            throw new IllegalArgumentException("Property with id " + id + " does not exist.");
        }

        propertyRepository.deleteById(id);
    }

    public boolean isOwnerOfProperty(Long propertyId, String username) {
        Optional<Property> propertyOpt = propertyRepository.findById(propertyId);
        if (propertyOpt.isEmpty()) {
            return false;
        }

        Property property = propertyOpt.get();
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return false;
        }

        User user = userOpt.get();
        return property.getOwner().equals(user);
    }

    public Optional<Property> getPropertiesByAddress(String address) {
        return propertyRepository.findByAddress(address);
    }

    public List<Long> countPropertiesByCities(List<String> cities) {
        return cities.stream()
                .map(city -> propertyRepository.countByCity(city))
                .collect(Collectors.toList());
    }

    public List<Property> getFeaturedProperties(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return propertyRepository.findFeaturedProperties(pageable);
    }

    public List<Property> findProperties(String city, Integer minPrice, Integer maxPrice) {
        return propertyRepository.findByCityAndCheapestPriceBetween(city, minPrice, maxPrice);
    }

    public Optional<Property> getPropertyById(Long id) {
        return propertyRepository.findById(id);
    }
}
