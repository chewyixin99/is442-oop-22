package com.is442.oop.auth;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.is442.oop.data.models.User;

@Component
public interface AuthService {

    public String getToken(User clientUser, HttpServletRequest request);
    public org.springframework.security.core.userdetails.User loadUserByUsername(String email) throws UsernameNotFoundException;

}
