package com.is442.oop.data.models;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uid;
    private String username;
    private String password;
    private String email;
    private String contactNumber;
    @Enumerated(EnumType.STRING)
    private UserType userType; // admin; borrower; gop
    private boolean defunct; // Soft deletion of users
    
    public Integer getId() {
        return uid;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
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
    
    @Override
    public String toString() {
        return "User [id=" + uid + ", username=" + username + ", password=" + password + ", email=" + email + ", contactNumber=" + contactNumber
                + ", userType=" + userType + ", defunct=" + defunct + "]";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        User user = (User) obj;
        return user.getId() == uid;
    }
}
