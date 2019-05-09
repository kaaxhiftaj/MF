package com.techease.mensfashion.communication.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by k.zahid on 3/28/18.
 */

public class BaseResponse {

    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("message")
    @Expose
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
