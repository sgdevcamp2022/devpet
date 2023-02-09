package com.example.petmily.model.data.auth;

public class Refresh_Token {
    String access_token;
    String token_type;
    String refresh_token;
    int expires_in;
    String scope;
    String jti;

    public Refresh_Token(String access_token, String token_type, String refresh_token, int expires_in, String scope, String jti) {
        this.access_token = access_token;
        this.token_type = "bearer";
        this.refresh_token = refresh_token;
        this.expires_in = expires_in;
        this.scope = "trust";
        this.jti = jti;
    }


}
