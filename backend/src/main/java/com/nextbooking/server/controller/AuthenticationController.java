package com.nextbooking.server.controller;

import com.nextbooking.server.model.User;
import com.nextbooking.server.model.UserLoginRequest;
import com.nextbooking.server.model.UserRegisterRequest;
import com.nextbooking.server.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }

    // Endpoint for user registration
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserRegisterRequest registerRequest){
        User registeredUser = authenticationService.registerUser(registerRequest);
        return ResponseEntity.ok(registeredUser);
    }

    // Endpoint for user authentication and generating JWT
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody UserLoginRequest loginRequest) {
        Map<String, Object> response = authenticationService.authenticateUser(loginRequest);
        return ResponseEntity.ok(response);
    }
}
