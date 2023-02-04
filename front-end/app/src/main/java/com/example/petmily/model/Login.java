package com.example.petmily.model;

public class Login {
    String grant_type;
    String username;
    String password;
    String scope;
    String phone;
    String age;
    String provider;
    String gender;
    String nickname;

    public Login(String username, String password) {
        this.grant_type = "password";
        this.username = username;
        this.password = password;
        this.scope = "trust";
        this.phone = null;
        this.age = null;
        this.provider = null;
        this.gender = null;
        this.nickname = null;
    }

    public Login(String username, String password, String phone, String age, String gender, String nickname) {
        this.grant_type = "password";
        this.username = username;
        this.password = password;
        this.scope = "trust";
        this.phone = phone;
        this.age = age;
        this.provider = "카카오";
        this.gender = gender;
        this.nickname = nickname;
    }
}
