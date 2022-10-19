package com.is442.oop.user;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.is442.oop.data.models.User;
import com.is442.oop.exception.ResourceNotFoundException;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public User getUser(Integer userId) throws ResourceNotFoundException {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "User ID", userId));
    };

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    };

    @Override
    public User createUser(CreateUserRequest createUserRequest) {
        User user = new User();
        user.setUsername(createUserRequest.getUsername());
        user.setPassword(createUserRequest.getPassword());
        user.setEmail(createUserRequest.getEmail());
        user.setContactNumber(createUserRequest.getContactNumber());
        user.setUserType(createUserRequest.getUserType());
        userRepository.save(user);
        return user;
    };

    @Override
    public User updateUser(Integer userId, UpdateUserRequest updateUserRequest) throws ResourceNotFoundException {
        User user = null;
        Optional<User> queryUser = userRepository.findById(userId);
        if (queryUser.isEmpty()) {
            throw new ResourceNotFoundException("User", "User ID", userId);
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
    public User deleteUser(Integer userId) throws ResourceNotFoundException {
        User user = null;
        Optional<User> queryUser = userRepository.findById(userId);
        if (queryUser.isEmpty()) {
            throw new ResourceNotFoundException("User", "User ID", userId);
        }
        user = queryUser.get();
        user.setDefunct(true);
        userRepository.save(user);
        return user;
    }
}
