package com.creovue.Creovue.controller;

import com.creovue.Creovue.dto.SignupRequest;
import com.creovue.Creovue.entity.User;
import com.creovue.Creovue.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody SignupRequest request) {
        User createdUser = authService.registerUser(request);
        return ResponseEntity.ok(createdUser);
    }

    
}