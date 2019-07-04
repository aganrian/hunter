package com.example.hunter.data.remote.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Historyreportbean implements Serializable {

    @SerializedName("data")
    private List<Data> dataList;

    public List<Data> getDataList() {
        return dataList;
    }

    public class Data implements Serializable {

        @SerializedName("id_lapor")
        private Integer id_lapor;

        @SerializedName("id_btmk")
        private Integer id_btmk;

        @SerializedName("id_user")
        private Integer id_user;

        @SerializedName("pict_tumbnail")
        private String pict_tumbnail;

        @SerializedName("pict_tumbnail2")
        private String pict_tumbnail2;

        @SerializedName("description")
        private String description;

        @SerializedName("lat_lapor")
        private String lat_lapor;

        @SerializedName("long_lapor")
        private String long_lapor;

        @SerializedName("dt_added")
        private String dt_added;

        @SerializedName("updated_at")
        private String updated_at;

        @SerializedName("created_at")
        private String created_at;

        @SerializedName("is_deleted")
        private Integer is_deleted;

        @SerializedName("flag_draft")
        private String flag_draft;

        @SerializedName("NO_CAR_POLICE")
        private String noCarPolice;

        @SerializedName("status")
        private String status;

        @SerializedName("point")
        private Integer point;

        @SerializedName("no_polisi")
        private String no_polisi;


        @SerializedName("CUSTOMER_NAME")
        private String customerName;

        @SerializedName("DESC_VEHICLE_BRAND")
        private String descVehicleBrand;

        @SerializedName("DESC_VEHICLE_TYPE")
        private String descVehicleType;

        @SerializedName("DESC_VEHICLE_MODEL")
        private String descVehicleModel;

        @SerializedName("VEHICLE_YEAR")
        private Integer vehicleYear;

        @SerializedName("OVERDUE_MONTH")
        private Integer overdueMonth;

        @SerializedName("partner_namer")
        private String partnerName;

        @SerializedName("status_handling")
        private String statusHandling;

        @SerializedName("expired_at")
        private String expiredAt;

        public String getExpiredAt() {
            return expiredAt;
        }

        public String getStatusHandling() {
            return statusHandling;
        }

        public String getNo_polisi() {
            return no_polisi;
        }

        public String getCustomerName() {
            return customerName;
        }

        public String getDescVehicleBrand() {
            return descVehicleBrand;
        }

        public String getDescVehicleType() {
            return descVehicleType;
        }

        public String getDescVehicleModel() {
            return descVehicleModel;
        }

        public Integer getVehicleYear() {
            return vehicleYear;
        }

        public Integer getOverdueMonth() {
            return overdueMonth;
        }

        public String getPartnerName() {
            return partnerName;
        }

        public String getStatus() {
            return status;
        }

        public Integer getPoint() {
            return point;
        }

        public String getNoCarPolice() {
            return noCarPolice;
        }

        public Integer getId_lapor() {
            return id_lapor;
        }

        public Integer getId_btmk() {
            return id_btmk;
        }

        public Integer getId_user() {
            return id_user;
        }

        public String getPict_tumbnail() {
            return pict_tumbnail;
        }

        public String getPict_tumbnail2() {
            return pict_tumbnail2;
        }

        public String getDescription() {
            return description;
        }

        public String getLat_lapor() {
            return lat_lapor;
        }

        public String getLong_lapor() {
            return long_lapor;
        }

        public String getDt_added() {
            return dt_added;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public String getCreated_at() {
            return created_at;
        }

        public Integer getIs_deleted() {
            return is_deleted;
        }

        public String getFlag_draft() {
            return flag_draft;
        }
    }


}
