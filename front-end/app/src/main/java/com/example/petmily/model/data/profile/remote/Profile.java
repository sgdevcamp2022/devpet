package com.example.petmily.model.data.profile.remote;

import com.example.petmily.model.data.profile.Pet;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Profile {

    @SerializedName("imageUri")
    String imageUri;

    @SerializedName("nickname")
    String nickname;

    @SerializedName("about")
    String about;

    @SerializedName("birth")
    String birth;

    @SerializedName("petList")
    List<Pet> petList;

    public Profile(String imageUri, String nickname, String about, String birth, List<Pet> petList) {
        this.imageUri = imageUri;
        this.nickname = nickname;
        this.about = about;
        this.birth = birth;
        this.petList = petList;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
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

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public List<Pet> getPetList() {
        return petList;
    }

    public void setPetList(List<Pet> petList) {
        this.petList = petList;
    }
}
