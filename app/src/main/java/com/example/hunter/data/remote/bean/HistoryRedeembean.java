package com.example.hunter.data.remote.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistoryRedeembean {

    @SerializedName("data")
    private List<Data> dataList;

    public List<Data> getDataList() {
        return dataList;
    }

    public class Data {

        @SerializedName("id")
        private Integer id;

        @SerializedName("table_user_id")
        private Integer table_user_id;

        @SerializedName("product_id")
        private Integer product_id;

        @SerializedName("status")
        private String status;

        @SerializedName("price")
        private Integer point;

        @SerializedName("no_resi")
        private String noresi;

        public Integer getPoint() {
            return point;
        }

        public String getNoresi() {
            return noresi;
        }

        @SerializedName("created_at")
        private String created_at;

        @SerializedName("updated_at")
        private String updated_at;

        @SerializedName("name")
        private String name;

        public String getStatus() {
            return status;
        }

        public Integer getId() {
            return id;
        }

        public Integer getProduct_id() {
            return product_id;
        }

        public Integer getTable_user_id() {
            return table_user_id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public String getName() {
            return name;
        }
    }


}
