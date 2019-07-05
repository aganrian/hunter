package com.example.hunter.data.remote.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProvinceBean {

    @SerializedName("data")
    private List<Data> dataList;

    public List<Data> getDataList() {
        return dataList;
    }

    public class Data{

            @SerializedName("id")
            private Integer id;

            @SerializedName("province")
            private String province;

            public String getProvince() {
                return province;
            }

            public Integer getId() {
                return id;
            }

            @SerializedName("regency")
            private String regency;

            public String getRegency() {
                return regency;
            }

            @SerializedName("district")
            private String district;

            public String getDistrict() {
                return district;
            }

            @SerializedName("village")
            private String village;

            public String getVillage() {
                return village;
            }

            @SerializedName("postcode")
            private String postcode;

            public String getPostcode() {
                return postcode;
            }
    }

}
