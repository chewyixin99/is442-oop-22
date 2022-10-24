package com.is442.oop.password;

import lombok.Data;

@Data
public class PasswordRequest {
    private String email;
    private String oldPassword;
    private String newPassword;
}