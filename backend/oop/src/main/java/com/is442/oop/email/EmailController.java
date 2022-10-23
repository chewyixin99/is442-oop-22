package com.is442.oop.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.is442.oop.data.payloads.response.DataResponse;

/**
 * EmailController to test EmailService independently
 */
@RestController
@RequestMapping("/emails")
public class EmailController {
    @Autowired
    EmailService emailService;

    @PostMapping("/sendAdminEmail")
    public ResponseEntity<DataResponse> sendGenericEmail(@RequestBody SendEmailRequest sendAdminEmailRequest) {
        try {
            emailService.sendAdminEmail(sendAdminEmailRequest.getLoanId(), sendAdminEmailRequest.getTemplateId());
        } catch (Exception e) {
            return new ResponseEntity<>(new DataResponse(null, e), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new DataResponse(null, "Email"), HttpStatus.OK);
    }

    @PostMapping("/sendPassConfirmationEmail")
    public ResponseEntity<DataResponse> sendPassConfirmationEmail(@RequestBody SendEmailRequest sendPassConfirmationEmailRequest) {
        try {
            emailService.sendPassConfirmationEmail(sendPassConfirmationEmailRequest.getLoanId(), sendPassConfirmationEmailRequest.getTemplateId());
        } catch (Exception e) {
            return new ResponseEntity<>(new DataResponse(null, e), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new DataResponse(null, "Email"), HttpStatus.OK);
    }
}
