package com.is442.oop.email;

import org.springframework.stereotype.Component;

import com.is442.oop.exception.ActionNotExecutedException;

@Component
public interface EmailService {
    void sendGenericEmail(String senderEmail, String recipientEmail, Integer templateId) throws ActionNotExecutedException;
}
