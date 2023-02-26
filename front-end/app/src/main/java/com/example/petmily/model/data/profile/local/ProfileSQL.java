package com.example.petmily.model.data.profile.local;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ProfileSQL")
public class ProfileSQL {

    @NonNull
    @PrimaryKey
    String name;

    String nickname;

    String about;

    int profileId;

    public ProfileSQL(@NonNull String name, String nickname, String about, int profileId) {
        this.name = name;
        this.nickname = nickname;
        this.about = about;
        this.profileId = profileId;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }
}
