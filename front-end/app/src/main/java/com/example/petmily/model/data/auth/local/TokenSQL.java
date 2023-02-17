package com.example.petmily.model.data.auth.local;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "TokenSQL")
public class TokenSQL {

    @NonNull
    @PrimaryKey
    String userId;

    String accessToken;

    String refreshToken;

    public TokenSQL(@NonNull String userId, String accessToken, String refreshToken) {
        this.userId = userId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    @NonNull
    public String getUid() {
        return userId;
    }

    public void setUid(@NonNull String userId) {
        this.userId = userId;
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
