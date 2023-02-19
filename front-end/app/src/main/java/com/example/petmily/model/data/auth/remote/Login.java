package com.example.petmily.model.data.auth.remote;

import com.google.gson.annotations.SerializedName;

public class Login {

    @SerializedName("grant_type")
    String grant_type;

    @SerializedName("username")
    String username;

    @SerializedName("password")
    String password;

    @SerializedName("scope")
    String scope;

    public Login(String username, String password) {
        this.grant_type = "password";
        this.username = username;
        this.password = password;
        this.scope = "trust";
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
