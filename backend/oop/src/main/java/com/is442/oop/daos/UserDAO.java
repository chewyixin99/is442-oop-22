package com.is442.oop.daos;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.is442.oop.data.models.User;
import com.is442.oop.data.payloads.request.CreateUserRequest;
import com.is442.oop.data.payloads.request.UpdateUserRequest;
import com.is442.oop.data.payloads.response.MessageResponse;
import com.is442.oop.data.repository.UserRepository;
import com.is442.oop.exceptions.ResourceNotFoundException;

@Service
public class UserDAO implements UserDAOInt {
    @Autowired
    UserRepository userRepository;

    @Override
    public User getUser(Integer userId) throws ResourceNotFoundException {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Resource not found."));
    };

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    };

    @Override
    public MessageResponse createUser(CreateUserRequest createUserRequest) {
        User user = new User();
        user.setUsername(createUserRequest.getUsername());
        user.setPassword(createUserRequest.getPassword());
        user.setEmail(createUserRequest.getEmail());
        user.setContactNumber(createUserRequest.getContactNumber());
        user.setUserType(createUserRequest.getUserType());
        user.setDefunct(createUserRequest.getDefunct());
        userRepository.save(user);
        return new MessageResponse(String.format("User created, ID: %d.", user.getId()));
    };

    @Override
    public User updateUser(Integer userId, UpdateUserRequest updateUserRequest) throws ResourceNotFoundException {
        User user = null;
        Optional<User> queryUser = userRepository.findById(userId);
        if (queryUser.isEmpty()) {
            throw new ResourceNotFoundException("Resource not found.");
        }

        user = queryUser.get();
        user.setUsername(updateUserRequest.getUsername());
        user.setEmail(updateUserRequest.getEmail());
        user.setContactNumber(updateUserRequest.getContactNumber());
        user.setUserType(updateUserRequest.getUserType());
        userRepository.save(user);
        return user;
    };

    @Override
    public void deleteUser(Integer userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId).get();
        if (user.getId().equals(userId)) {
            System.out.println(user);
            user.setDefunct(true);
            userRepository.save(user);
        } else {
            throw new ResourceNotFoundException("Resource not found.");
        }
    };
}
