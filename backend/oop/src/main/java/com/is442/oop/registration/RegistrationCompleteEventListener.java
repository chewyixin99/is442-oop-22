package com.is442.oop.registration;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.is442.oop.data.models.User;
import com.is442.oop.user.UserService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j // used to log verification 'email' to console, reimplement with email, to remove
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        // create verification token for the user link
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.saveVerificationTokenForUser(token, user);

        // send email to user
        String url = event.getApplicationUrl() + "/verifyRegistration?token=" + token;
        // ideally this is where we send the email, 'sendVerificationEmail()'
        log.info("Click the link to verify your account {}", url); // to reimplement with email instead of logging just to console
    }
}