package com.library.controller;

import com.library.dto.ApiResponse;
import com.library.dto.UserLoginDto;
import com.library.dto.UserRegistrationDto;
import com.library.entity.User;
import com.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    /**
     * Register new user
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@Valid @RequestBody UserRegistrationDto registrationDto) {
        ApiResponse<String> response = userService.registerUser(registrationDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    
    /**
     * Login user
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@Valid @RequestBody UserLoginDto loginDto) {
        ApiResponse<String> response = userService.loginUser(loginDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    
    /**
     * Get user info
     */
    @GetMapping("/user-info")
    public ResponseEntity<ApiResponse<User>> getUserInfo(HttpServletRequest request) {
        try {
            String token = request.getHeader("Authorization");
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
                Optional<User> userOpt = userService.validateTokenAndGetUser(token);
                if (userOpt.isPresent()) {
                    User user = userOpt.get();
                    // Don't return password in response
                    user.setPassword(null);
                    return ResponseEntity.ok(ApiResponse.success("User info retrieved successfully", user));
                }
            }
            return ResponseEntity.status(401).body(ApiResponse.error("Invalid or expired token", 401));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ApiResponse.error("Failed to get user info: " + e.getMessage(), 500));
        }
    }
    
    /**
     * Health check
     */
    @GetMapping("/health")
    public ResponseEntity<ApiResponse<String>> health() {
        return ResponseEntity.ok(ApiResponse.success("Auth service is running", null));
    }
}


