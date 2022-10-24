package com.is442.oop.verification;

import org.springframework.data.jpa.repository.JpaRepository;

import com.is442.oop.data.models.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Integer> {

    VerificationToken findByToken(String token);
    
}

