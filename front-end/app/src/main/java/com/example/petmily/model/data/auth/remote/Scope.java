package com.example.petmily.model.data.auth.remote;

public class Scope {
    String trust;

    public Scope(String trust) {
        this.trust = trust;
    }

    public String getTrust() {
        return trust;
    }

    public void setTrust(String trust) {
        this.trust = trust;
    }
}
