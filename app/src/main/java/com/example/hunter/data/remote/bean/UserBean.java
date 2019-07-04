package com.example.hunter.data.remote.bean;

import com.google.gson.annotations.SerializedName;

public class UserBean {

    @SerializedName("data")
    private LoginBean data;

    public LoginBean getData() {
        return data;
    }
}
