package com.devpet.feed.data.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserInfoDto {


    private String userId;
    private String nickname;
    private String birth;
    private String address;
    private String gender;


    @Builder
    public UserInfoDto(String userId, String nickname, String birth, String address, String gender) {

        this.userId = userId;
        this.nickname = nickname;
        this.birth = birth;
        this.address = address;
        this.gender = gender;
    }
}