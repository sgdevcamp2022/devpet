package com.example.petmily.model.data.post.remote;


import java.util.List;

public class Success {
    String txId;
    boolean isSuccess;
    List<Result> result;


    public Success(String txId, boolean isSuccess, List<Result> result) {
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

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }
}
