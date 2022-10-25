package com.is442.oop.password;

import org.springframework.data.jpa.repository.JpaRepository;

import com.is442.oop.data.models.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer> {

    PasswordResetToken findByToken(String token);
    
}