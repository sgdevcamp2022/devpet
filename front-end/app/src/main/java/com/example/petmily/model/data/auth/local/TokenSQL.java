package com.example.petmily.model.data.auth.local;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "TokenSQL")
public class TokenSQL {

    @NonNull
    @PrimaryKey
    String uid;

    String accessToken;

    String refreshToken;

    public TokenSQL(@NonNull String uid, String accessToken, String refreshToken) {
        this.uid = uid;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    @NonNull
    public String getUid() {
        return uid;
    }

    public void setUid(@NonNull String uid) {
        this.uid = uid;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
