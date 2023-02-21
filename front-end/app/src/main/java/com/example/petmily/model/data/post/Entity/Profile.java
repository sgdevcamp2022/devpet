package com.example.petmily.model.data.post.Entity;

public class Profile {

    String profileImage;
    String userId;
    String nickname;

    public Profile(String profileImage, String userId, String nickname) {
        this.profileImage = profileImage;
        this.userId = userId;
        this.nickname = nickname;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
