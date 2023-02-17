package com.example.petmily.model.data.auth;

public class RefreshToken {

    String access_token;
    String token_type;
    String refresh_token;
    int expires_in;
    String scope;
    String userId;
    String jti;


    public RefreshToken(String access_token, String token_type, String refresh_token, int expires_in, String scope, String userId, String jti) {
        this.access_token = access_token;
        this.token_type = "bearer";
        this.refresh_token = refresh_token;
        this.expires_in = expires_in;
        this.scope = "trust";
        this.userId = userId;
        this.jti = jti;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUid() {
        return userId;
    }

    public void setUid(String userId) {
        this.userId = userId;
    }

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }

    @Override
    public String toString() {
        return "RefreshToken{" +
                "access_token='" + access_token + '\'' +
                ", token_type='" + token_type + '\'' +
                ", refresh_token='" + refresh_token + '\'' +
                ", expires_in=" + expires_in +
                ", scope='" + scope + '\'' +
                ", userId='" + userId + '\'' +
                ", jti='" + jti + '\'' +
                '}';
    }
}
