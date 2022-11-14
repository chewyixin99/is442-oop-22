package com.is442.oop.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import com.is442.oop.data.models.User;
import com.is442.oop.jwttoken.JwtTokenService;
import com.is442.oop.user.UserRepository;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    UserRepository userRepository;

    @Override
    public String getToken(User clientUser, HttpServletRequest request) {

        org.springframework.security.core.userdetails.User user = loadUserByUsername(clientUser.getEmail());

        UsernamePasswordAuthenticationToken userCred = new UsernamePasswordAuthenticationToken(
                user,
                user,
                user.getAuthorities());

        userCred.setDetails(
                new WebAuthenticationDetailsSource()
                        .buildDetails((HttpServletRequest) request));

        Authentication auth = userCred;

        String token = jwtTokenService.generateToken(auth);

        return token;
    }

    @Override
    public org.springframework.security.core.userdetails.User loadUserByUsername(String email)
            throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null)
            throw new UsernameNotFoundException("User not found.");

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                getAuthorities(
                        List.of(user.getUserType().toString())));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

}
