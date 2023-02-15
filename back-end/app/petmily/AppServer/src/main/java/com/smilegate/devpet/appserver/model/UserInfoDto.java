package com.smilegate.devpet.appserver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {
    private String userId;
    private String nickname;
    private String birth;
    private String address;
    private String gender;
    private Set<Follow> followers;

    private Set<Post> posts;
}