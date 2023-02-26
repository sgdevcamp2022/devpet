package com.example.petmily.model.data.profile.remote;

import com.example.petmily.model.data.profile.Entity.Follow;

import java.util.List;

public class SuccessFollow {
    String txId;
    boolean isSuccess;
    List<Follow> result;

    public SuccessFollow(String txId, boolean isSuccess, List<Follow> result) {
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

    public List<Follow> getResult() {
        return result;
    }

    public void setResult(List<Follow> result) {
        this.result = result;
    }
}
