package com.library.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UserRegistrationDto {
    
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^09\\d{8}$", message = "Phone number must be valid Taiwan mobile number")
    private String phoneNumber;
    
    @NotBlank(message = "Password is required")
    private String password;
    
    @NotBlank(message = "User name is required")
    private String userName;
    
    // Constructors
    public UserRegistrationDto() {}
    
    public UserRegistrationDto(String phoneNumber, String password, String userName) {
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.userName = userName;
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
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    @Override
    public String toString() {
        return "UserRegistrationDto{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}


