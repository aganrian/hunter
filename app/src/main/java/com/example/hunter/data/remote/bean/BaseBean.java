package com.example.hunter.data.remote.bean;

import com.google.gson.annotations.SerializedName;

public class BaseBean {

        @SerializedName("status")
        private String status;

        public String getStatus() {
        return status;
         }
}
