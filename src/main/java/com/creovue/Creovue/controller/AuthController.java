package com.creovue.Creovue.controller;

import com.creovue.Creovue.dto.LoginRequest;
import com.creovue.Creovue.dto.SignupRequest;
import com.creovue.Creovue.dto.UserResponse;
import com.creovue.Creovue.entity.User;
import com.creovue.Creovue.mapper.UserMapper;
import com.creovue.Creovue.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

//    @PostMapping("/signup")
//    public ResponseEntity<User> signup(@RequestBody SignupRequest request) {
//        User createdUser = authService.registerUser(request);
//        return ResponseEntity.ok(createdUser);
//    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(@Valid @RequestBody SignupRequest request) {
        User user = authService.registerUser(request); // use AuthService
        return ResponseEntity.ok(UserMapper.toResponse(user));
    }



    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String token = authService.loginUser(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(token);
    }
}