package com.is442.oop.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.is442.oop.data.payloads.response.DataResponse;

/**
 * EmailController to test EmailService independently
 */
@RestController
@RequestMapping
public class EmailController {
    @Autowired
    EmailService emailService;

    @PostMapping("/sendGenericEmail")
    public ResponseEntity<DataResponse> sendGenericEmail() {
        try {
            emailService.sendGenericEmail("lohkokwee@gmail.com", 1);
        } catch (Exception e) {
            return new ResponseEntity<>(new DataResponse(null, e), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new DataResponse(null, "Email"), HttpStatus.BAD_REQUEST);
    }
}
