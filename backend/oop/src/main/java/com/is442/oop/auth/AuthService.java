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
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import com.is442.oop.data.models.User;
import com.is442.oop.jwttoken.JwtTokenService;
import com.is442.oop.user.UserRepository;

@Service
public class AuthService {
    
    private static final Logger LOG = LoggerFactory.getLogger(AuthService.class);

    // private final InMemoryUserDetailsManager inMemoryUserDetailsManager;

    private final JwtTokenService jwtTokenService;

    @Autowired
    UserRepository userRepository;

    public AuthService(
        // InMemoryUserDetailsManager inMemoryUserDetailsManager,
        JwtTokenService jwtTokenService
    ) {
        this.jwtTokenService = jwtTokenService;
        // this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
    }

    public String getToken(
        // Authentication authentication,
        User clientUser
    ) {
        
        // how to make inmemoryuserdetailsmanager useful?
        // uncomment this to debug at a later date
        
        // inMemoryUserDetailsManager.createUser(
        //     org.springframework.security.core.userdetails.User
        //         .withUsername(clientUser.getUsername())
        //         .password(clientUser.getPassword()).
        //         roles(clientUser.getUserType().toString())
        //         .build()
        // );

        org.springframework.security.core.userdetails.User user = 
            loadUserByUsername(clientUser.getEmail());

        Authentication newAuthentication = 
            new UsernamePasswordAuthenticationToken(
                user,
                user // add credentials here?
            );

        // System.out.println();
        // System.out.println("newAuthentication: ");
        // System.out.println(newAuthentication.getClass());
        // System.out.println(newAuthentication.getCredentials());
        // System.out.println(newAuthentication.getDetails());
        // System.out.println(newAuthentication.getName());
        // System.out.println(newAuthentication.getPrincipal());
        // System.out.println(newAuthentication.getAuthorities());
        // System.out.println(newAuthentication.toString());

        // System.out.printf("Token requested for user: %s", newAuthentication.getName());
        // System.out.println();

        String token = jwtTokenService.generateToken(newAuthentication);

        // System.out.printf("Token granted: %s", token);
        // System.out.println();

        return token;

        // LOG.debug("Token requested for user: '{}", authentication.getName());
        // System.out.printf("Token requested for user: %s", authentication.getName());
        // System.out.println();

        // String token = jwtTokenService.generateToken(authentication);
        
        // LOG.debug("Token granted {}", token);
        // System.out.printf("Token granted: %s", token);
        // System.out.println();

        // return token;

        // return clientUser.getEmail();
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
