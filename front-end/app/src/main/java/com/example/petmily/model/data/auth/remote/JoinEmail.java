package com.example.petmily.model.data.auth.remote;

import com.google.gson.annotations.SerializedName;

public class JoinEmail {

    @SerializedName("username")
    private String username;

    @SerializedName("nickname")
    private String nickname;

    @SerializedName("name")
    private String name;

    @SerializedName("password")
    private String password;

    @SerializedName("age")
    private String age;

    @SerializedName("gender")
    private String gender;

    @SerializedName("phone")
    private String phone;

    @SerializedName("provider")
    private String provider;


    public JoinEmail(String username, String name, String password) {//카카오 1차 회원가입
        this.username = username;
        this.nickname = null;
        this.name = name;
        this.password = password;
        this.age = null;
        this.gender = null;
        this.phone = null;
        this.provider = null;
    }

    public JoinEmail(String username, String nickname, String age, String gender, String phone) {
        this.username = username;
        this.nickname = nickname;
        this.name = "";
        this.password = "";
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.provider = "카카오";
    }

    //이메일 회원가입
    public JoinEmail(String username, String nickname, String name, String password, String age, String gender, String phone, String provider) {
        this.username = username;
        this.nickname = nickname;
        this.name = name;
        this.password = password;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.provider = provider;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    @Override
    public String toString() {
        return "Join{" +
                "username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", age='" + age + '\'' +
                ", gender='" + gender + '\'' +
                ", phone='" + phone + '\'' +
                ", provider='" + provider + '\'' +
                '}';
    }
}
