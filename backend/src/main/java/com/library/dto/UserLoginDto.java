package com.library.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UserLoginDto {
    
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^09\\d{8}$", message = "Phone number must be valid Taiwan mobile number")
    private String phoneNumber;
    
    @NotBlank(message = "Password is required")
    private String password;
    
    // Constructors
    public UserLoginDto() {}
    
    public UserLoginDto(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
    
    // Getters and Setters
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public String toString() {
        return "UserLoginDto{" +
                "phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}


