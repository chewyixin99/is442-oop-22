package com.is442.oop.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.is442.oop.data.models.User;
import com.is442.oop.jwttoken.JwtTokenService;
import com.is442.oop.user.UserRepository;

@Service
public class AuthService {
    
    private final JwtTokenService jwtTokenService;

    @Autowired
    UserRepository userRepository;

    public AuthService(
        JwtTokenService jwtTokenService
    ) {
        this.jwtTokenService = jwtTokenService;
    }

    public String getToken(
        User clientUser
    ) {
        
        org.springframework.security.core.userdetails.User user = 
            loadUserByUsername(clientUser.getEmail());

        Authentication auth = 
            new UsernamePasswordAuthenticationToken(
                user,
                user, // add credentials here?
                user.getAuthorities() 
            );

        System.out.println(auth);
        System.out.println(auth.isAuthenticated());

        String token = jwtTokenService.generateToken(auth);

        return token;
    }
 
    public org.springframework.security.core.userdetails.User loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        
        if (user == null) throw new UsernameNotFoundException("User not found.");

        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
            user.isEnabled(),
            true,
            true,
            true,
            getAuthorities(
                List.of(user.getUserType().toString())
            )
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

}
