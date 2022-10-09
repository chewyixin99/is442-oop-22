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
    private String contactNumber;
    @NotBlank
    @NotNull
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @NotBlank
    @NotNull
    private boolean defunct;
    
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getContactNumber() {
        return contactNumber;
    }
    public void setContactNumber(String contactNumber) {
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
    public boolean getDefunct() {
        return defunct;
    }
    public void setDefunct(boolean defunct) {
        this.defunct = defunct;
    }
}