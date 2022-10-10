package com.is442.oop.data.payloads.request;

import javax.validation.constraints.*;


public class CreateUserRequest extends UpdateUserRequest {
    @NotBlank
    @NotNull
    private String password;

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }
}
