package com.example.petmily.model.data.auth;

public class AccessToken {
    String token;
    String message;

    public AccessToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
