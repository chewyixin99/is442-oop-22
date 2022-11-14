package com.is442.oop.user;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.is442.oop.data.models.PasswordResetToken;
import com.is442.oop.data.models.User;
import com.is442.oop.data.models.VerificationToken;
import com.is442.oop.exception.ActionNotExecutedException;
import com.is442.oop.exception.ResourceNotFoundException;
import com.is442.oop.password.PasswordResetTokenRepository;
import com.is442.oop.verification.VerificationTokenRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User getUser(Integer userId) throws ResourceNotFoundException {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "User ID", userId));
    };

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    };

    // Deprecated
    // @Override
    // public User createUser(UserRequest userRequest) {
    //     User user = new User();
    //     user.setUsername(userRequest.getUsername());
    //     user.setPassword(userRequest.getPassword());
    //     user.setEmail(userRequest.getEmail());
    //     user.setContactNumber(userRequest.getContactNumber());
    //     user.setUserType(userRequest.getUserType());
    //     userRepository.save(user);
    //     return user;
    // };

    @Override
    public User updateUser(Integer userId, UserRequest userRequest) throws ResourceNotFoundException {
        User user = null;
        Optional<User> queryUser = userRepository.findById(userId);
        if (queryUser.isEmpty()) {
            throw new ResourceNotFoundException("User", "User ID", userId);
        }

        user = queryUser.get();
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setContactNumber(userRequest.getContactNumber());
        user.setUserType(userRequest.getUserType());
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

    @Override
    public User registerUser(UserRequest userRequest) throws IllegalArgumentException, ActionNotExecutedException {
        System.out.println("UserController: registerUser");

        User existingUser = null;
        existingUser = userRepository.findByUsername(userRequest.getUsername());
        if (existingUser instanceof User) {
            throw new IllegalArgumentException(String.format("User with username (%s) already exists.", userRequest.getUsername()));
        }

        existingUser = userRepository.findByEmail(userRequest.getEmail());
        if (existingUser instanceof User) {
            throw new IllegalArgumentException(String.format("User with email (%s) already exists.", userRequest.getEmail()));
        }

        User user = new User();
        try {
            user.setEmail(userRequest.getEmail());
            user.setUsername(userRequest.getUsername());
            user.setUserType(userRequest.getUserType()); // can be dynamic later
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            user.setContactNumber(userRequest.getContactNumber());
            userRepository.save(user);
        } catch (Exception e) {
            throw new ActionNotExecutedException("registerUser", e);
        }
        return user;
    }

    @Override
    public void saveVerificationTokenForUser(String token, User user) {
        VerificationToken verificationToken = new VerificationToken(user, token);
        
        // send email

        // save verification to database
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public String validateVerificationToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);

        if (verificationToken == null) {
            return "invalid";
        }
        
        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();

        if ((verificationToken.getExpirationTime().getTime() - cal.getTime().getTime()) <= 0) {
            verificationTokenRepository.delete(verificationToken);
            return "expired";
        }

        user.setEnabled(true);
        userRepository.save(user);

        return "valid";
    }

    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(oldToken);
        if (verificationToken == null) return null;
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken passwordResetToken = new PasswordResetToken(user, token);
        passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    public String validatePasswordResetToken(String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);

        if (passwordResetToken == null) {
            return "invalid";
        }
        
        Calendar cal = Calendar.getInstance();

        if ((passwordResetToken.getExpirationTime().getTime() - cal.getTime().getTime()) <= 0) {
            passwordResetTokenRepository.delete(passwordResetToken);
            return "expired";
        }

        return "valid";
    }

    @Override
    public Optional<User> getUserByPasswordResetToken(String token) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getUser());
    }

    @Override
    public void changePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public boolean checkIfValidOldPassword(User user, String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }
}
