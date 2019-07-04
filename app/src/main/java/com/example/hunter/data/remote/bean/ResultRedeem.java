package com.example.hunter.data.remote.bean;

import com.google.gson.annotations.SerializedName;

public class ResultRedeem {

    @SerializedName("status")
    private String status;

    @SerializedName("point_change")
    private Integer pointChange;

    @SerializedName("point")
    private Integer point;

    public Integer getPoint() {
        return point;
    }

    public String getStatus() {
        return status;
    }

    public Integer getPointChange() {
        return pointChange;
    }
}
