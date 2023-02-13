package com.example.oauth.data.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PasswordDto {

    private String nowPassword;
    private String newPassword;


    @Builder
    public PasswordDto(String nowPassword, String newPassword) {
        this.nowPassword = nowPassword;
        this.newPassword = newPassword;
    }

}
