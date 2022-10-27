package com.is442.oop.authorization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.is442.oop.jwttoken.JwtTokenService;

@RestController
public class AuthorizationController {
    
    private static final Logger LOG = LoggerFactory.getLogger(AuthorizationController.class);

    private final JwtTokenService jwtTokenService;

    public AuthorizationController(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @PostMapping("/token")
    public String token(Authentication authentication) {

        LOG.debug("Token requested for user: '{}", authentication.getName());
        System.out.printf("Token requested for user: %s", authentication.getName());
        System.out.println();

        String token = jwtTokenService.generateToken(authentication);
        
        LOG.debug("Token granted {}", token);
        System.out.printf("Token granted: %s", token);
        System.out.println();

        return token;
    }

}
