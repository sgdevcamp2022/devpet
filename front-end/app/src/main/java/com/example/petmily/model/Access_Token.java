package com.example.petmily.model;

public class Access_Token implements TempInterface{
    String token;
    String message;

    public Access_Token(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    @Override
    public String getRoomId() {
        return null;
    }
}
