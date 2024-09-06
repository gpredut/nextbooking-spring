package com.nextbooking.server.service;

import com.nextbooking.server.model.User;
import com.nextbooking.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        // Find the user either by username or email
        User user = userRepository.findByUsername(usernameOrEmail)
                .orElseGet(() -> userRepository.findByEmail(usernameOrEmail)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email: " + usernameOrEmail)));

        // Return the UserDetails object required by Spring Security
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername()) // Use username for the UserDetails principal
                .password(user.getPassword())      // Ensure password is encoded
                .authorities(user.getRole().name()) // Set user roles
                .build();
    }
}
