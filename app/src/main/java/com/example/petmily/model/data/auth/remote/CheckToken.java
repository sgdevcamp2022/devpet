package com.example.petmily.model.data.auth.remote;

import java.util.List;

public class CheckToken {
    //사용하는 값
    String uid;
    String user_name; //이메일



    List<Scope> scope; //trust 고정
    String active; //true 고정
    String exp; //아마 변동값
    List<Authorities> authorities;
    String jti;
    String client_id;

    public CheckToken(String udi, String user_name)
    {
        this.uid = uid;
        this.user_name = user_name;
    }

    public CheckToken(String uid, String user_name, List<Scope> scope, String active, String exp, List<Authorities> authorities, String jti, String client_id) {
        this.uid = uid;
        this.user_name = user_name;
        this.scope = scope;
        this.active = active;
        this.exp = exp;
        this.authorities = authorities;
        this.jti = jti;
        this.client_id = client_id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public List<Scope> getScope() {
        return scope;
    }

    public void setScope(List<Scope> scope) {
        this.scope = scope;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public List<Authorities> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authorities> authorities) {
        this.authorities = authorities;
    }

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }
}
