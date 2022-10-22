package com.is442.oop.email;

import org.springframework.stereotype.Component;

import com.is442.oop.exception.ActionNotExecutedException;

@Component
public interface EmailService {
    void sendGenericEmail(String recipientEmail, Integer templateId) throws ActionNotExecutedException;
    void sendAttachmentEmail(String senderEmail, String recipientEmail, Integer templateId, Integer loanId) throws ActionNotExecutedException;
}
