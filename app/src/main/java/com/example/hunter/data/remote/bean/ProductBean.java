package com.example.hunter.data.remote.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductBean {


    @SerializedName("data")
    private List<Data> dataList;

    public List<Data> getDataList() {
        return dataList;
    }

    public class Data  {

        @SerializedName("id")
        private Integer idProduct;

        @SerializedName("name")
        private String name;

        @SerializedName("price")
        private Integer price;

        @SerializedName("image")
        private String image;

        @SerializedName("created_at")
        private String createAt;

        @SerializedName("updated_at")
        private String updatedAt;

        @SerializedName("deleted_at")
        private String deletedAt;

        public Integer getPrice() {
            return price;
        }

        public Integer getIdProduct() {
            return idProduct;
        }

        public String getName() {
            return name;
        }

        public String getImage() {
            return image;
        }

        public String getCreateAt() {
            return createAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public String getDeletedAt() {
            return deletedAt;
        }
    }


}
