package com.example.petmily.model.data.profile.remote;

import com.example.petmily.model.data.profile.Entity.Follower;

import java.util.List;

public class SuccessFollower {
    String txId;
    boolean isSuccess;
    List<Follower> result;

    public SuccessFollower(String txId, boolean isSuccess, List<Follower> result) {
        this.txId = txId;
        this.isSuccess = isSuccess;
        this.result = result;
    }

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public List<Follower> getResult() {
        return result;
    }

    public void setResult(List<Follower> result) {
        this.result = result;
    }
}
