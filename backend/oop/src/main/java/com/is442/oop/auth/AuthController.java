package com.is442.oop.auth;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.is442.oop.data.models.User;
import com.is442.oop.data.payloads.response.DataResponse;
import com.is442.oop.exception.ResourceNotFoundException;
import com.is442.oop.user.UserRequest;
import com.is442.oop.user.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;

    @Operation(summary="Login", description="Login", responses={
        @ApiResponse(responseCode = "200", description = "OK", content=@Content(schema=@Schema(implementation = DataResponse.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized. User not activated yet", content=@Content), 
        @ApiResponse(responseCode="403", description="Forbidden. Invalid email", content=@Content),
        @ApiResponse(responseCode = "404", description = "User not found", content=@Content)
    })
    @PostMapping("/login")
    public ResponseEntity<DataResponse> loginEmail(@RequestBody UserRequest userRequest) {
        User user = userService.findUserByEmail(userRequest.getEmail());

        if (user == null) {
            return new ResponseEntity<>(new DataResponse(
                userRequest.getEmail(),
                new ResourceNotFoundException("login", "email", userRequest.getEmail()) 
            ), HttpStatus.NOT_FOUND);
        }

        if (!(user.isEnabled())) {
            return new ResponseEntity<>(
                new DataResponse(userRequest.getEmail(), new IllegalArgumentException("Invalid user. User has not been activated.")),
                HttpStatus.UNAUTHORIZED);
        }

        if (user.isDefunct()) {
            return new ResponseEntity<>(new DataResponse(userRequest.getEmail(), new IllegalArgumentException("Invalid user. User has been deactivated.")),
                HttpStatus.FORBIDDEN);
        }

        boolean isPasswordMatch = userService.checkIfValidOldPassword(user, userRequest.getPassword());
        if (!(isPasswordMatch)) {
            return new ResponseEntity<>(new DataResponse(user.getEmail(), "Invalid password, please try again"),
                    HttpStatus.UNAUTHORIZED);
        }

        HttpServletRequest request = 
            ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        String jwtToken = authService.getToken(user, request);

        return new ResponseEntity<>(new DataResponse(jwtToken, "User logged in"), HttpStatus.OK);
    }
}
