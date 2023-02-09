package com.example.oauth.data.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class ProfileDto {

    private String my;

    // 사용자 이름
    private String name;

    // 이메일
    private String username;

    private String nickname;

    private ArrayList<String> imageUrl;

    @Builder
    public ProfileDto(String my, String name, String username, String nickname, ArrayList<String> imageUrl) {
        this.my = my;
        this.name = name;
        this.username = username;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
    }
}
