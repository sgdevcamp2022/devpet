package com.example.petmily.model.data.profile.remote;

import java.util.List;

public class SuccessFollow {
    String txId;
    boolean isSuccess;
    List<Profile> result;

    public SuccessFollow(String txId, boolean isSuccess, List<Profile> result) {
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

    public List<Profile> getResult() {
        return result;
    }

    public void setResult(List<Profile> result) {
        this.result = result;
    }
}
