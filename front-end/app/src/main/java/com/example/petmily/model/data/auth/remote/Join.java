package com.example.petmily.model.data.auth.remote;

import com.google.gson.annotations.SerializedName;

public class Join {

    @SerializedName("username")
    private String username;

    @SerializedName("nickname")
    private String nickname;

    @SerializedName("name")
    private String name;

    @SerializedName("password")
    private String password;

    public Join(String username, String nickname, String name, String password) {
        this.username = username;
        this.nickname = nickname;
        this.name = name;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
