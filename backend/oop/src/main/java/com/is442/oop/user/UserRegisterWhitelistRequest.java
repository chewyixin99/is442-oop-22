package com.is442.oop.user;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.is442.oop.data.models.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterWhitelistRequest {
    @NotBlank
    @NotNull
    private String username;
    @NotBlank
    @NotNull
    @Email
    private String email;
    @NotBlank
    @NotNull
    private String contactNumber;
    @NotBlank
    @NotNull
    @Enumerated(EnumType.STRING)
    private UserType userType;
}
