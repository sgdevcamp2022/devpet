package com.example.petmily.model.data.profile.Entity;

public class Follow {

    int profileId;
    String nickname;
    String imageUrl;

    public Follow(int profileId, String nickname, String imageUrl) {
        this.profileId = profileId;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
