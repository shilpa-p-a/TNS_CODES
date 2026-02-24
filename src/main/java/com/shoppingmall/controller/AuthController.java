package com.shoppingmall.controller;

import com.shoppingmall.entity.User;
import com.shoppingmall.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    public String signup(@RequestBody User user) {
        userRepository.save(user);
        return "User signed up successfully with role: " + user.getType();
    }

    @PostMapping("/login")
    public User login(@RequestBody User credentials) {
        Optional<User> user = userRepository.findByNameAndPassword(credentials.getName(), credentials.getPassword());
        return user.orElseThrow(() -> new RuntimeException("Invalid credentials"));
    }
}
