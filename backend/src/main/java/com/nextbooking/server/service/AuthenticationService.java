package com.nextbooking.server.service;

import com.nextbooking.server.model.User;
import com.nextbooking.server.model.UserLoginRequest;
import com.nextbooking.server.model.UserRegisterRequest;
import com.nextbooking.server.repository.UserRepository;
import com.nextbooking.server.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public User registerUser(UserRegisterRequest registerRequest) {
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new IllegalStateException("Username already exists: " + registerRequest.getUsername());
        }
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new IllegalStateException("Email already exists: " + registerRequest.getEmail());
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        // Set role to GUEST if not provided
        user.setRole(registerRequest.getRole() != null ? registerRequest.getRole() : User.Role.GUEST);

        return userRepository.save(user);
    }

    public Map<String, Object> authenticateUser(UserLoginRequest loginRequest) {
        String username = loginRequest.getUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("Invalid credentials: " + username));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new IllegalStateException("Invalid credentials for user: " + username);
        }

        // Get user role
        String role = user.getRole().name();

        // Generate token with role
        String token = jwtUtil.generateToken(user.getUsername(), role);

        Map<String, Object> response = new HashMap<>();
        response.put("user", user);
        response.put("token", token);

        return response;
    }
}
