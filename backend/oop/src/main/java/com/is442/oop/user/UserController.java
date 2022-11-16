package com.is442.oop.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.is442.oop.data.models.User;
import com.is442.oop.data.payloads.response.DataResponse;

import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @Operation(summary = "Gets all users", description = "Gets all users")
    @GetMapping("")
    public ResponseEntity<DataResponse> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(new DataResponse(users, "User"), HttpStatus.OK);
    }

    @Operation(summary = "Get user by id", description = "Get user by id")
    @GetMapping("/{userId}")
    public ResponseEntity<DataResponse> getUser(@PathVariable("userId") Integer userId) {
        User user = null;
        try {
            user = userService.getUser(userId);
        } catch (Exception e) {
            return new ResponseEntity<>(new DataResponse(user, e), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new DataResponse(user, "User"), HttpStatus.OK);
    }

    // Deprecated
    // Use registrationController /register route instead
    // @PostMapping("")
    // public ResponseEntity<DataResponse> createUser(@RequestBody UserRequest userRequest) {
    //     User user = userService.createUser(userRequest);
    //     return new ResponseEntity<>(new DataResponse(user, "User"), HttpStatus.CREATED);
    // }

    // To change password, use registrationController /changePassword instead
    @Operation(summary = "Update user", description = "Updates the user's details")
    @PutMapping("/{userId}")
    public ResponseEntity<DataResponse> updateUser(@PathVariable("userId") Integer userId, @RequestBody UserRequest userRequest) {
        User updateUser = null;
        try {
            updateUser = userService.updateUser(userId, userRequest);
        } catch (Exception e) {
            return new ResponseEntity<>(new DataResponse(updateUser, e), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new DataResponse(updateUser, "User"), HttpStatus.OK);
    }

    @Operation(summary = "Soft deletes a user", description = "Soft deletes a user by setting \"defunct\" attribute to true")
    @DeleteMapping("/{userId}")
    public ResponseEntity<DataResponse> deleteUser(@PathVariable("userId") Integer userId) {
        User user = null;
        try {
            user = userService.deleteUser(userId);
        } catch (Exception e) {
            return new ResponseEntity<>(new DataResponse(user, e), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new DataResponse(user, "User"), HttpStatus.OK);
    }
}
