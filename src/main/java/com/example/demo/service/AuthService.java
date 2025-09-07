package com.example.demo.service;

import com.example.demo.model.AuthResponse;
import com.example.demo.model.LoginRequest;
import com.example.demo.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    // Demo users - in real app, use database
    private final Map<String, User> users = new HashMap<>();
    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public AuthService() {
        // Initialize demo users
        users.put("admin", new User(1L, "admin", "password", "admin@demo.com", "ADMIN"));
        users.put("user", new User(2L, "user", "password", "user@demo.com", "USER"));
    }

    public AuthResponse authenticate(LoginRequest loginRequest) {
        User user = users.get(loginRequest.getUsername());
        
        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            String token = generateToken(user);
            return new AuthResponse(token, user.getUsername(), user.getRole(), "Login successful");
        }
        
        return new AuthResponse(null, null, null, "Invalid credentials");
    }

    private String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("role", user.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour
                .signWith(secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}