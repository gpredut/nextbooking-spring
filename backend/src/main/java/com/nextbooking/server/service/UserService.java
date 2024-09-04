package com.nextbooking.server.service;

import com.nextbooking.server.model.User;
import com.nextbooking.server.model.UserLoginRequest;
import com.nextbooking.server.model.UserRegisterRequest;
import com.nextbooking.server.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public User registerUser(UserRegisterRequest registerRequest){
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword());

        user.setRole(registerRequest.getRole() !=null ? registerRequest.getRole() : User.Role.GUEST);

        return userRepository.save(user);
    }

    public User authenticateUser(UserLoginRequest loginRequest){
        Optional<User> userOpt = userRepository.findByUsername(loginRequest.getUsernameOrEmail());

        if (!userOpt.isPresent()){
            userOpt = userRepository.findByEmail(loginRequest.getUsernameOrEmail());
        }
        User user = userOpt.orElseThrow(() -> new RuntimeException(("Invalid credentials")));

        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid credentials");
        }
        return user;
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getUsersByRole(User.Role role) {
        return userRepository.findByRole(role);
    }

    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(updatedUser.getUsername());
                    user.setEmail(updatedUser.getEmail());

                    if (!passwordEncoder.matches(updatedUser.getPassword(), user.getPassword())) {
                        user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                    }

                    user.setRole(updatedUser.getRole());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
