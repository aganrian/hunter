package com.example.hunter.data.remote.bean;

import com.google.gson.annotations.SerializedName;

public class VehicleReportBean {


    @SerializedName("status")
    private String status;

    public String getStatus() {
        return status;
    }

    @SerializedName("user_id")
    private Integer userId;

    @SerializedName("partner_id")
    private Integer partnerId;

    @SerializedName("already_member")
    private Integer alreadyMember;

    public Integer getAlreadyMember() {
        return alreadyMember;
    }

    public Integer getPartnerId() {
        return partnerId;
    }

    public Integer getUserId() {
        return userId;
    }
}
