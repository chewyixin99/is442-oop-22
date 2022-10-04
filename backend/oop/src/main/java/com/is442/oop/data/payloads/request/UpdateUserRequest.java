package com.is442.oop.data.payloads.request;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.is442.oop.data.models.*;

public class UpdateUserRequest {
    @NotBlank
    @NotNull
    private String username;
    @NotBlank
    @NotNull
    @Email
    private String email;
    @NotBlank
    @NotNull
    private int contactNumber;
    @NotBlank
    @NotNull
    @Enumerated(EnumType.STRING)
    private UserType userType;
    
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public int getContactNumber() {
        return contactNumber;
    }
    public void setContactNumber(int contactNumber) {
        this.contactNumber = contactNumber;
    }

    public UserType getUserType() {
        return userType;
    }
    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}