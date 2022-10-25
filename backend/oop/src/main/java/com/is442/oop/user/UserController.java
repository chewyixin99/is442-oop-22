package com.is442.oop.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.is442.oop.data.models.User;
import com.is442.oop.data.payloads.response.DataResponse;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;
    
    @GetMapping("")
    public ResponseEntity<DataResponse> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(new DataResponse(users, "User"), HttpStatus.OK);
    }

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

    @PostMapping("")
    public ResponseEntity<DataResponse> createUser(@RequestBody UserRequest userRequest) {
        User user = userService.createUser(userRequest);
        return new ResponseEntity<>(new DataResponse(user, "User"), HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<DataResponse> updateUser(@PathVariable("userId") Integer userId,
            @RequestBody UserRequest userRequest) {
        User updateUser = null;
        try {
            updateUser = userService.updateUser(userId, userRequest);
        } catch (Exception e) {
            return new ResponseEntity<>(new DataResponse(updateUser, e), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new DataResponse(updateUser, "User"), HttpStatus.OK);
    }

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
