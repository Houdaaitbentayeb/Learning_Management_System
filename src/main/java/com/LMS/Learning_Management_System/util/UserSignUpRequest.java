package com.LMS.Learning_Management_System.util;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class UserSignUpRequest {
    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    @NotNull(message = "User type ID cannot be null")
    @Positive(message = "User type ID must be positive")
    private Integer userTypeId;  // Changé de int à Integer

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(Integer userTypeId) {
        this.userTypeId = userTypeId;
    }
}