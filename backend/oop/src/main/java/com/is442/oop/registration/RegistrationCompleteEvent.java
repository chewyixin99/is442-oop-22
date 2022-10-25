package com.is442.oop.registration;

import org.springframework.context.ApplicationEvent;

import com.is442.oop.data.models.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {

    private User user;
    private String applicationUrl; // url that we have to create for the user when we send the email

    public RegistrationCompleteEvent(User user, String applicationUrl) {
        super(user);
        this.user = user;
        this.applicationUrl = applicationUrl;
    }
    
}