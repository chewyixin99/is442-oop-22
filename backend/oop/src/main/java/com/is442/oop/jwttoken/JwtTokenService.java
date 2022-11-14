package com.is442.oop.jwttoken;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public interface JwtTokenService {
    public String generateToken(Authentication authentication);

}