package com.is442.oop.registration;

import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.is442.oop.data.models.User;
import com.is442.oop.data.models.VerificationToken;
import com.is442.oop.data.payloads.response.DataResponse;
import com.is442.oop.password.PasswordRequest;
import com.is442.oop.user.UserRequest;
import com.is442.oop.user.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j // used to log verification 'email' to console, reimplement with email, to remove
public class RegistrationController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @PostMapping("/register")
    public ResponseEntity<DataResponse> registerUser(@RequestBody UserRequest userRequest, final HttpServletRequest request) {
        User user = userService.registerUser(userRequest);
        publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));
        return new ResponseEntity<>(new DataResponse(user, "User registration success"), HttpStatus.OK);
    }

    @GetMapping("/verifyRegistration")
    public ResponseEntity<DataResponse> verifyRegistration(@RequestParam("token") String token) {
        String result = userService.validateVerificationToken(token);
        if (result.equalsIgnoreCase("valid")) {
            return new ResponseEntity<>(new DataResponse(result, "User verification success"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new DataResponse(result, "User verification failed, bad user, token is " + result), HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/resendVerificationToken")
    public ResponseEntity<DataResponse> resentVerificationToken(
        @RequestParam("token") String oldToken,
        HttpServletRequest request
    ) {
        VerificationToken verificationToken = userService.generateNewVerificationToken(oldToken);

        if (verificationToken == null) {
            return new ResponseEntity<>(new DataResponse(verificationToken, "Resend verification token failed, old token is invalid"), HttpStatus.NOT_FOUND);
        }

        User user = verificationToken.getUser();

        // send email
        resendVerificationTokenMail(user, applicationUrl(request), verificationToken);

        // return "Verification link sent";
        return new ResponseEntity<>(new DataResponse(user, "Resend verification token"), HttpStatus.OK);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<DataResponse> resetPassword(@RequestBody PasswordRequest passwordRequest, HttpServletRequest request) {
        User user = userService.findUserByEmail(passwordRequest.getEmail());
        String url = "";
        if (user != null) {
            String token = UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(user, token);
            url = passwordResetTokenMail(user, applicationUrl(request), token);
            return new ResponseEntity<>(new DataResponse(url, "Password reset successfully"), HttpStatus.OK);
        }
        
        return new ResponseEntity<>(new DataResponse(passwordRequest.getEmail(), "Password reset failed, email is invalid: " + passwordRequest.getEmail()), HttpStatus.NOT_FOUND);
    }

    @PostMapping("/savePassword")
    public ResponseEntity<DataResponse> savePassword(
        @RequestParam("token") String token,
        @RequestBody PasswordRequest passwordRequest
    ) {
        String result = userService.validatePasswordResetToken(token);
        if (!result.equalsIgnoreCase("valid")) {
            return new ResponseEntity<>(new DataResponse(result, "Save password failed, token is " + result), HttpStatus.UNAUTHORIZED);
        }
        Optional<User> user = userService.getUserByPasswordResetToken(token);
        if (user.isPresent()) {
            userService.changePassword(user.get(), passwordRequest.getNewPassword());
            return new ResponseEntity<>(new DataResponse(result, "Password reset successfully"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new DataResponse(result, "Save password failed, token is " + result), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/changePassword")
    public ResponseEntity<DataResponse> changePassword(@RequestBody PasswordRequest passwordRequest) {
        User user = userService.findUserByEmail(passwordRequest.getEmail()); // handle exception: null pointer exception if user does not exist
        if (user == null) {
            return new ResponseEntity<>(new DataResponse(user, "Invalid user email"), HttpStatus.NOT_FOUND);
        }
        if (!userService.checkIfValidOldPassword(user, passwordRequest.getOldPassword())) {
            return new ResponseEntity<>(new DataResponse(user, "Invalid old password"), HttpStatus.UNAUTHORIZED);
        }
        // Save new password
        userService.changePassword(user, passwordRequest.getNewPassword());
        return new ResponseEntity<>(new DataResponse(user, "Password changed successfully"), HttpStatus.OK);
    }

    private String passwordResetTokenMail(
        User user,
        String applicationUrl,
        String token
    ) {
        // send email to user
        String url = applicationUrl + "/savePassword?token=" + token;
        // ideally this is where we send the email, 'sendVerificationEmail()'
        log.info("Click the link to reset your password {}", url); // to reimplement with email instead of logging jsut to console

        return url;
    }

    private void resendVerificationTokenMail(
        User user, 
        String applicationUrl,
        VerificationToken verificationToken
    ) {
        // send email to user
        String url = applicationUrl + "/verifyRegistration?token=" + verificationToken.getToken();
        // ideally this is where we send the email, 'sendVerificationEmail()'
        log.info("Click the link to verify your account {}", url); // to reimplement with email instead of logging jsut to console
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}

