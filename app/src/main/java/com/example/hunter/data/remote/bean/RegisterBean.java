package com.example.hunter.data.remote.bean;

import com.google.gson.annotations.SerializedName;

/*variable response balikan dari register aplikasi*/
public class RegisterBean {

    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private LoginBean data;

    public String getStatus() {
        return status;
    }

    public LoginBean getData() {
        return data;
    }


}
