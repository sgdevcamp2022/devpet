package com.example.petmily.model.data.make.remote;

public class SuccessMessage {
    String txId;
    boolean isSuccess;
    int result;

    public SuccessMessage(String txId, boolean isSuccess, int result) {
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

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
