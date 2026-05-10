package com.employeeSystem.RoleBasedSystem.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.employeeSystem.RoleBasedSystem.dao.UserRepo;
import com.employeeSystem.RoleBasedSystem.entity.User;
import com.employeeSystem.RoleBasedSystem.jwt.JwtUtil;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserRepo repo;

    @Autowired
    private JwtUtil jwtUtil;

    public UserController(UserRepo repo) {
        this.repo = repo;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody Map<String, String> req) {

        String username = req.get("username");
        String password = req.get("password");

        System.out.println(username + password);

        User user = repo.findByUsername(username);

        if (user == null ||
                !user.getPassword().equals(password)) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                            "message",
                            "Invalid credentials"));
        }

        String token = jwtUtil.generateToken(
                user.getUsername(),
                user.getRole());

        return ResponseEntity.ok(Map.of(

                "token", token,
                "username", user.getUsername(),
                "role", user.getRole(),
                "message", "Login Successful"

        ));
    }
}