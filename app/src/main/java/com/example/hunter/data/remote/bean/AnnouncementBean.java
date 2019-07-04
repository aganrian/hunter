package com.example.hunter.data.remote.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AnnouncementBean  {

    @SerializedName("data")
    private Data data;

    public Data getData() {
        return data;
    }

    public class Data{

        @SerializedName("data")
        private List<Data2> data2;

        public List<Data2> getData2() {
            return data2;
        }

        public class Data2{

            @SerializedName("id")
            private Integer id;

            @SerializedName("title")
            private String title;

            @SerializedName("content")
            private String content;

            @SerializedName("created_at")
            private String created_at;

            @SerializedName("updated_at")
            private String updated_at;

            public Integer getId() {
                return id;
            }

            public String getTitle() {
                return title;
            }

            public String getContent() {
                return content;
            }

            public String getCreated_at() {
                return created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }
        }


    }


}
