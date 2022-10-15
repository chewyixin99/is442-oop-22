package com.is442.oop.data.models;

import javax.persistence.*;

import org.springframework.beans.factory.annotation.Value;

@Entity
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer userId;
    
    @Column(name = "username", nullable = false)
    private String username;
    
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "contact_number", nullable = false)
    private String contactNumber;

    @Column(name = "user_type")
    @Enumerated(EnumType.STRING)
    private UserType userType; // admin; borrower; gop

    @Column(name = "defunct", nullable = false)
    @Value("false") // Default value
    private boolean defunct;
    
    public Integer getId() {
        return userId;
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
        return "User [id=" + userId + ", username=" + username + ", password=" + password + ", email=" + email + ", contactNumber=" + contactNumber
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
        return user.getId() == userId;
    }
}
