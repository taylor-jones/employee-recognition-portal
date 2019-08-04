package com.ttt.erp.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PasswordResetRequest {

    private String username;

    @JsonProperty(value = "newPassword")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
