package com.example.shoh_oauth.data.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private String name;
    private String username; // email
    private String age;
    private String password;
    private String gender;
    private String nickname;
    private String phone;
    private String provider;


    @Builder
    public UserDto(String username, String age, String gender, String nickname, String phone, String provider, String password, String name) {
        this.name = name;
        this.username = username;
        this.age = age;
        this.gender = gender;
        this.nickname = nickname;
        this.phone = phone;
        this.provider = provider;
        this.password = password;
    }
}