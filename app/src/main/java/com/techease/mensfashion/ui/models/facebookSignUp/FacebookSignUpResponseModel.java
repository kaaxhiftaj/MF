
package com.techease.mensfashion.ui.models.facebookSignUp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FacebookSignUpResponseModel {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("user")
    @Expose
    private UserDataModel user;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public UserDataModel getUser() {
        return user;
    }

    public void setUser(UserDataModel user) {
        this.user = user;
    }

}
