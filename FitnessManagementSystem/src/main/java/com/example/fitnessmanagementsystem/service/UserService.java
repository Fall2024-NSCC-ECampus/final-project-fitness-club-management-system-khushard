package com.example.fitnessmanagementsystem.service;

import com.example.fitnessmanagementsystem.model.User;
import com.example.fitnessmanagementsystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Authenticates a user by verifying input credentials.
     * @param username The username provided by the user trying to log in.
     * @param password The password provided.
     * @return The authenticated User object if the credentials are valid.
     * @throws RuntimeException If the user is not found or the password isn't correct.
     * */
    public User login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Wrong password");
        }
        return user;
    }
}
