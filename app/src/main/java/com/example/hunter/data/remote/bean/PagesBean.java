package com.example.hunter.data.remote.bean;

import com.google.gson.annotations.SerializedName;

public class PagesBean {


    @SerializedName("data")
    private Data data;

    public Data getData() {
        return data;
    }

    public class Data{



        @SerializedName("id")
        private Integer id;

        @SerializedName("title")
        private String title;

        @SerializedName("content")
        private String content;

        public Integer getId() {
            return id;
        }

        public String getContent() {
            return content;
        }

        public String getTitle() {
            return title;
        }
    }

}

