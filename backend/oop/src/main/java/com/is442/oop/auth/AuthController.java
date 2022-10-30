package com.is442.oop.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.is442.oop.data.models.User;
import com.is442.oop.data.payloads.response.DataResponse;
import com.is442.oop.user.UserRequest;
import com.is442.oop.user.UserService;

@RestController
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<DataResponse> loginEmail(
        @RequestBody UserRequest userRequest
    ) {
        User user = userService.findUserByEmail(userRequest.getEmail());
        if (user == null) {
            return new ResponseEntity<>(new DataResponse(userRequest.getEmail(), "Invalid email. Unable to find user."), HttpStatus.NOT_FOUND);
        }
        if (!(user.isEnabled())) {
            return new ResponseEntity<>(new DataResponse(userRequest.getEmail(), "Invalid user. User has not been activated."), HttpStatus.UNAUTHORIZED);
        }
        boolean isPasswordMatch = userService.checkIfValidOldPassword(user, userRequest.getPassword());
        if (!(isPasswordMatch)) {
            return new ResponseEntity<>(new DataResponse(user.getEmail(), "Invalid password, please try again"), HttpStatus.UNAUTHORIZED);
        }

        String jwtToken = authService.getToken(user);

        return new ResponseEntity<>(new DataResponse(jwtToken, "User logged in"), HttpStatus.OK);
    }
}
