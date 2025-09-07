package com.example.demo.service;

import com.example.demo.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class UserService {

    private final Map<Long, User> users = new HashMap<>();

    public UserService() {
        // Initialize demo data
        users.put(1L, new User(1L, "admin", "password", "admin@demo.com", "ADMIN"));
        users.put(2L, new User(2L, "user", "password", "user@demo.com", "USER"));
        users.put(3L, new User(3L, "john", "password", "john@demo.com", "USER"));
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public User getUserById(Long id) {
        return users.get(id);
    }

    public User getUserByUsername(String username) {
        return users.values().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }
}