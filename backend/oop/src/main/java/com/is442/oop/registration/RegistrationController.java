package com.is442.oop.registration;

import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

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
import com.is442.oop.email.EmailService;
import com.is442.oop.exception.ActionNotExecutedException;
import com.is442.oop.password.PasswordRequest;
import com.is442.oop.user.UserRegisterRequest;
import com.is442.oop.user.UserRegisterWhitelistRequest;
import com.is442.oop.user.UserRequest;
import com.is442.oop.user.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j // used to log verification 'email' to console, reimplement with email, to remove
public class RegistrationController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private EmailService emailService;


    // Deprecated (can delete)
    // @PostMapping("/register")
    // public ResponseEntity<DataResponse> registerUser(@RequestBody UserRequest userRequest, final HttpServletRequest request) {
    //     System.out.println("RegistrationController: registerUser");
    //     User user = null;            
    //     try {
    //         user = userService.registerUser(userRequest);
    //     } catch (IllegalArgumentException e) {
    //         return new ResponseEntity<>(new DataResponse(user, e), HttpStatus.NOT_ACCEPTABLE);
    //     } catch (Exception e) {
    //         return new ResponseEntity<>(new DataResponse(user, e), HttpStatus.INTERNAL_SERVER_ERROR);
    //     }

    //     publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));
    //     return new ResponseEntity<>(new DataResponse(user, "User registration success"), HttpStatus.OK);
    // }

    @PostMapping("/registerOverride")
    @Operation(summary = "Register a user with without a whitelist" , description="Register a user with without a whitelist. This function is used for testing purposes", responses={
        @ApiResponse(responseCode = "200", description = "User registered", content=@Content(schema=@Schema(implementation = DataResponse.class))),
        @ApiResponse(responseCode="406", description="Illegal Argument Exception", content=@Content), 
        @ApiResponse(responseCode="500", description="Internal server error", content=@Content)
    })
    @Transactional
    public ResponseEntity<DataResponse> registerOverride(@RequestBody UserRequest userRequest, final HttpServletRequest request) {
        System.out.println("RegistrationController: registerOverride");
        User user = null;            
        try {
            user = userService.registerUserOverride(userRequest);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new DataResponse(user, e), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(new DataResponse(user, e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Prepare token for email
        String url = null;
        try {
            String token = UUID.randomUUID().toString();
            userService.saveVerificationTokenForUser(token, user);
            // url = this.applicationUrl(request) + "/verifyRegistration?token=" + token;
            url = "http://localhost:8888?token=" + token;
        } catch (Exception e) {
            return new ResponseEntity<>(new DataResponse(user, e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        // Send email
        try {
            emailService.sendSimpleConfirmationUrlEmail(user, 1, url);
        } catch (Exception e) {
            return new ResponseEntity<>(new DataResponse(user, e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(new DataResponse(user, "User registration success"), HttpStatus.OK);
    }

    @PostMapping("/register")
    @Operation(summary = "Register user", description = "Registers a user and sends them an email with a confirmation link", responses={
        @ApiResponse(responseCode = "200", description = "User registered", content=@Content(schema=@Schema(implementation = DataResponse.class))),
        @ApiResponse(responseCode="406", description="Illegal Argument Exception", content=@Content),
        @ApiResponse(responseCode="500", description="Internal server error", content=@Content)
    })
    @Transactional
    public ResponseEntity<DataResponse> registerUser(@RequestBody UserRegisterRequest userRequest, final HttpServletRequest request) {
        System.out.println("RegistrationController: registerUser");
        User user = null;            
        try {
            user = userService.registerUser(userRequest);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new DataResponse(user, e), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(new DataResponse(user, e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Prepare token for email
        String url = null;
        try {
            String token = UUID.randomUUID().toString();
            userService.saveVerificationTokenForUser(token, user);
            // url = this.applicationUrl(request) + "/verifyRegistration?token=" + token;
            url = "http://localhost:8888?token=" + token;
        } catch (Exception e) {
            return new ResponseEntity<>(new DataResponse(user, e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        // Send email
        try {
            emailService.sendSimpleConfirmationUrlEmail(user, 1, url);
        } catch (Exception e) {
            return new ResponseEntity<>(new DataResponse(user, e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(new DataResponse(user, "User registration success"), HttpStatus.OK);
    }

    @Operation(summary="Whitelists a user", description="Whitelists a user so that they can register to use the application. If the user is not whitelisted, they cannot register for the application", responses={
        @ApiResponse(responseCode = "200", description = "User whitelisted", content=@Content(schema=@Schema(implementation = DataResponse.class))),
        @ApiResponse(responseCode="401", description="Unauthorized", content=@Content),
        @ApiResponse(responseCode="406", description="Illegal Argument Exception", content=@Content),
        @ApiResponse(responseCode="500", description="Internal server error", content=@Content)
    })
    @PostMapping("/whitelist")
    public ResponseEntity<DataResponse> whitelistUser(@RequestBody UserRegisterWhitelistRequest userRequest) {
        User user = null;            
        try {
            user = userService.addUserForRegistration(userRequest);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new DataResponse(user, e), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(new DataResponse(user, e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(new DataResponse(user, "User successfully whitelisted"), HttpStatus.OK);
    }

    @Operation(summary="Verifies the user registration", description="Verifies the user registration", responses={
        @ApiResponse(responseCode = "200", description = "User registration verified", content=@Content(schema=@Schema(implementation = DataResponse.class))),
        @ApiResponse(responseCode="401", description="Unauthorized", content=@Content),
    })
    @GetMapping("/verifyRegistration")
    public ResponseEntity<DataResponse> verifyRegistration(@RequestParam("token") String token) {
        String result = userService.validateVerificationToken(token);
        if (result.equalsIgnoreCase("valid")) {
            return new ResponseEntity<>(new DataResponse(result, "User verification success"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new DataResponse(result, "User verification failed, bad user, token is " + result), HttpStatus.UNAUTHORIZED);
    }

    @Operation(summary="Resends the user registration email", description="Resends the user registration email", responses={
        @ApiResponse(responseCode = "200", description = "User registration email resent", content=@Content(schema=@Schema(implementation = DataResponse.class))),
        @ApiResponse(responseCode="404", description="Old token is not found", content=@Content),
    })
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
        resendVerificationTokenMail(user, applicationUrl(request), verificationToken);

        // return "Verification link sent";
        return new ResponseEntity<>(new DataResponse(user, "Resend verification token"), HttpStatus.OK);
    }

    @Operation(summary="Changes a user password", description="Change a user password", responses={
        @ApiResponse(responseCode = "200", description = "User password successfully changed", content=@Content(schema=@Schema(implementation = DataResponse.class))),
        @ApiResponse(responseCode="404", description="Email is invalid", content=@Content)
    })
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

    @Operation(summary="Changes a user password", description="Changes a user password", responses={
        @ApiResponse(responseCode = "200", description = "User password successfully changed", content=@Content(schema=@Schema(implementation = DataResponse.class))),
        @ApiResponse(responseCode="401", description="Password save failed. Unauthorized.", content=@Content),
        @ApiResponse(responseCode="404", description="Token is invalid", content=@Content)
    })
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

    @Operation(summary="Changes a user password", description="Changes a user password", responses={
        @ApiResponse(responseCode="200", description="User password successfully changed", content=@Content(schema=@Schema(implementation = DataResponse.class))),
        @ApiResponse(responseCode="401", description="Unauthorized. Invalid old password", content=@Content),
        @ApiResponse(responseCode="404", description="User not found", content=@Content), 
    })
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
        // String url = applicationUrl + "/savePassword?token=" + token;
        String url =  "http://localhost:8888/resetPassword?token=" + token +"&email=" + user.getEmail();
        try {
            emailService.sendSimpleConfirmationUrlEmail(user, 2, url);
        } catch (Exception e) {
            throw new ActionNotExecutedException("passwordResetTokenMail", e);
        }

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
        try {
            emailService.sendSimpleConfirmationUrlEmail(user, 1, url);
        } catch (Exception e) {
            throw new ActionNotExecutedException("resendVerificationTokenMail", e);
        }

        log.info("Click the link to verify your account {}", url); // to reimplement with email instead of logging jsut to console
    }
    
    private String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}

