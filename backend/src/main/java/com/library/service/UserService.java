package com.library.service;

import com.library.dto.ApiResponse;
import com.library.dto.UserLoginDto;
import com.library.dto.UserRegistrationDto;
import com.library.entity.User;
import com.library.repository.UserRepository;
import com.library.util.JwtUtil;
import com.library.util.PasswordUtil;
import com.library.util.SqlInjectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordUtil passwordUtil;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private SqlInjectionUtil sqlInjectionUtil;
    
    /**
     * Register new user
     */
    public ApiResponse<String> registerUser(UserRegistrationDto registrationDto) {
        try {
            // Check for SQL injection
            if (sqlInjectionUtil.containsSqlInjection(registrationDto.getPhoneNumber()) ||
                sqlInjectionUtil.containsSqlInjection(registrationDto.getUserName())) {
                return ApiResponse.error("Invalid input detected", 400);
            }
            
            // Check if phone number already exists
            if (userRepository.existsByPhoneNumber(registrationDto.getPhoneNumber())) {
                return ApiResponse.error("Phone number already registered", 409);
            }
            
            // Encode password
            String encodedPassword = passwordUtil.encodePassword(registrationDto.getPassword());
            
            // Create new user
            User user = new User(
                registrationDto.getPhoneNumber(),
                encodedPassword,
                registrationDto.getUserName()
            );
            
            // Save user
            userRepository.save(user);
            
            return ApiResponse.success("User registered successfully", null);
            
        } catch (Exception e) {
            return ApiResponse.error("Registration failed: " + e.getMessage(), 500);
        }
    }
    
    /**
     * Login user
     */
    public ApiResponse<String> loginUser(UserLoginDto loginDto) {
        try {
            // Check for SQL injection
            if (sqlInjectionUtil.containsSqlInjection(loginDto.getPhoneNumber())) {
                return ApiResponse.error("Invalid input detected", 400);
            }
            
            // Find user by phone number
            Optional<User> userOpt = userRepository.findByPhoneNumber(loginDto.getPhoneNumber());
            
            if (userOpt.isEmpty()) {
                return ApiResponse.error("Invalid phone number or password", 401);
            }
            
            User user = userOpt.get();
            
            // Check password
            if (!passwordUtil.matches(loginDto.getPassword(), user.getPassword())) {
                return ApiResponse.error("Invalid phone number or password", 401);
            }
            
            // Update last login time
            user.setLastLoginTime(LocalDateTime.now());
            userRepository.save(user);
            
            // Generate JWT token
            String token = jwtUtil.generateToken(user.getUserId(), user.getPhoneNumber());
            
            return ApiResponse.success("Login successful", token);
            
        } catch (Exception e) {
            return ApiResponse.error("Login failed: " + e.getMessage(), 500);
        }
    }
    
    /**
     * Get user by phone number
     */
    public Optional<User> getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }
    
    /**
     * Get user by ID
     */
    public Optional<User> getUserById(Integer userId) {
        return userRepository.findById(userId);
    }
    
    /**
     * Validate token and get user
     */
    public Optional<User> validateTokenAndGetUser(String token) {
        try {
            String phoneNumber = jwtUtil.getPhoneNumberFromToken(token);
            if (jwtUtil.validateToken(token, phoneNumber)) {
                return getUserByPhoneNumber(phoneNumber);
            }
            return Optional.empty();
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
