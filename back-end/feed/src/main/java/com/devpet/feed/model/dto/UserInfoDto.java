package com.devpet.feed.model.dto;

import com.devpet.feed.model.entity.UserInfo;
import com.devpet.feed.model.relationship.Follow;
import com.devpet.feed.model.relationship.Post;
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

    public UserInfoDto(UserInfo userInfo) {
        this.userId = userInfo.getUserId();
        this.nickname = userInfo.getNickname();
        this.birth = userInfo.getBirth();
        this.address = userInfo.getAddress();
        this.gender = userInfo.getGender();
        this.followers = userInfo.getFollowers();
    }
}
