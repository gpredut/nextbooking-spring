package com.nextbooking.server.service;

import com.nextbooking.server.model.User;
import com.nextbooking.server.model.UserLoginRequest;
import com.nextbooking.server.model.UserRegisterRequest;
import com.nextbooking.server.repository.UserRepository;
import com.nextbooking.server.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthenticationService(UserRepository userRepository, JwtUtil jwtUtil){
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.jwtUtil = jwtUtil;
    }

    public User registerUser(UserRegisterRequest registerRequest){
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(registerRequest.getRole()  != null ? registerRequest.getRole() : User.Role.GUEST );

        return userRepository.save(user);
    }

    public String authenticateUser(UserLoginRequest loginRequest){
        String usernameOrEmail = loginRequest.getUsernameOrEmail();

        // Try to find user by username
        Optional <User> userOpt = userRepository.findByUsername(usernameOrEmail);
        // If not found by username, try to find by email
        if(!userOpt.isPresent()){
            userOpt = userRepository.findByEmail(usernameOrEmail);
        }
        User user = userOpt.orElseThrow(() -> new RuntimeException("Invalid credentials"));

        // Validate password
        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid credentials");
        }

        // Determine which identifier to use for generating the token
        String identifier = user.getEmail().equals(usernameOrEmail) ? user.getEmail() : user.getUsername();

        // Generate and return JWT using the chosen identifier
        return jwtUtil.generateToken(identifier);
    }
}
