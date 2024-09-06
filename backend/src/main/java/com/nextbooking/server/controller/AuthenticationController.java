package com.nextbooking.server.controller;

import com.nextbooking.server.model.User;
import com.nextbooking.server.model.UserLoginRequest;
import com.nextbooking.server.model.UserRegisterRequest;
import com.nextbooking.server.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> loginUser(@RequestBody UserLoginRequest loginRequest){
        String token = authenticationService.authenticateUser(loginRequest);
        return ResponseEntity.ok(token);
    }
}
