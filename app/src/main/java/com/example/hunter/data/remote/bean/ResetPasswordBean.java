package com.example.hunter.data.remote.bean;

import com.google.gson.annotations.SerializedName;

/*variable response dari reset password*/
public class ResetPasswordBean {

    @SerializedName("id_user")
    private Integer id_user;

    public Integer getId_user() {
        return id_user;
    }
}
