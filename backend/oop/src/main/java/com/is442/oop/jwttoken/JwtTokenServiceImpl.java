package com.is442.oop.jwttoken;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenServiceImpl implements JwtTokenService {
    
    @Autowired
    private JwtEncoder jwtEncoder;

    @Override
    public String generateToken(Authentication authentication) {
        Instant now = Instant.now(); // timestamp
        String scope = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer("self") // self signing JWTs
            .issuedAt(now)
            .expiresAt(now.plus(3, ChronoUnit.HOURS)) // expires in an hour
            .subject(authentication.getName()) // princiapl / subject
            .claim("scope", scope) // scope defined above
            .build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

}