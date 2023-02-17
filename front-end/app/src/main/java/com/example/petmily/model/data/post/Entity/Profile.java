package com.example.petmily.model.data.post.Entity;

public class Profile {

    String profileImage;
    int userId;
    String nickname;

    public Profile(String profileImage, int userId, String nickname) {
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
