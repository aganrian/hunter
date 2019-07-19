package com.example.hunter.data.remote.bean;

import com.google.gson.annotations.SerializedName;

/*Variable Dasar yang di miliki oleh setiap API*/
public class BaseBean {

        @SerializedName("status")
        private String status;

        public String getStatus() {
        return status;
         }
}
