package com.example.hunter.data.remote.bean;

import com.google.gson.annotations.SerializedName;

/*variable balikan dari response ocr api*/
public class OcrBean {

    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private Data data;

    public Data getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public class Data {

        @SerializedName("id_btmk")
        private Integer vehicleId;

        @SerializedName("partner_id")
        private Integer partnerId;

        @SerializedName("NO_AGGR")
        private String noAggr;

        @SerializedName("ST_AGGR")
        private String stAggr;

        @SerializedName("NO_CAR_POLICE")
        private String noCarPolice;

        @SerializedName("DT_GOLIVE")
        private String dtGolive;

        @SerializedName("ST_UNIT")
        private String stUnit;

        @SerializedName("CD_VEHICLE_BRAND")
        private Integer cdVehicleBrand;

        @SerializedName("DESC_VEHICLE_BRAND")
        private String descVehilceBrand;

        @SerializedName("CD_VEHICLE_TYPE")
        private String cdVehilceType;

        @SerializedName("DESC_VEHICLE_TYPE")
        private String descVehicleType;

        @SerializedName("CD_VEHICLE_MODEL")
        private String cdVehicleModel;

        @SerializedName("DESC_VEHICLE_MODEL")
        private String descVehicleModel;

        @SerializedName("CD_VEHICLE_KIND")
        private String cdVehicleKind;

        @SerializedName("NO_CHASIS")
        private String noChasis;

        @SerializedName("NO_ENGINE")
        private String noEngine;

        @SerializedName("CD_COLOR")
        private String cdColor;

        @SerializedName("COLOR")
        private String color;

        @SerializedName("CD_VEHICLE_CATEGORY")
        private Integer cdVehicleCategory;

        @SerializedName("CD_VEHICLE_SUB_CATE")
        private Integer cdVehicleSubCate;

        @SerializedName("DESC_VEHICLE_SUB_CATE")
        private String descVehcileSubcate;

        @SerializedName("CD_SP")
        private String cdSp;

        @SerializedName("DESC_SP")
        private String descCp;

        @SerializedName("CD_SP_COLL")
        private Double cdSpColl;

        @SerializedName("AREA2")
        private String area2;

        @SerializedName("created_at")
        private String createAt;

        @SerializedName("VEHICLE_YEAR")
        private Integer vehicleYear;

        @SerializedName("VEHICLE_CATEGORY")
        private String vehicleCategory;

        @SerializedName("CUSTOMER_NAME")
        private String customerName;

        @SerializedName("OVERDUE_MONTH")
        private Integer overdueMonth;

        @SerializedName(value = "name",alternate = {"partner_name"})
        private String leasingName;

        public String getLeasingName() {
            return leasingName;
        }

        @SerializedName("table_user_id")
        private Integer tableUserId;

        @SerializedName("table_btmk_id")
        private Integer table_bmtk_id;

        @SerializedName("status_handling")
        private String status_handling;

        @SerializedName("expired_at")
        private String expired_at;

        @SerializedName("id_lapor")
        private Integer idLapor;

        public Integer getIdLapor() {
            return idLapor;
        }

        public Integer getTable_bmtk_id() {
            return table_bmtk_id;
        }

        public Integer getTableUserId() {
            return tableUserId;
        }

        public String getExpired_at() {
            return expired_at;
        }

        public String getStatus_handling() {
            return status_handling;
        }

        public Integer getOverdueMonth() {
            return overdueMonth;
        }

        public Integer getVehicleYear() {
            return vehicleYear;
        }

        public String getCustomerName() {
            return customerName;
        }

        public String getVehicleCategory() {
            return vehicleCategory;
        }

        public Integer getVehicleId() {
            return vehicleId;
        }

        public Integer getPartnerId() {
            return partnerId;
        }

        public String getNoAggr() {
            return noAggr;
        }

        public String getStAggr() {
            return stAggr;
        }

        public String getNoCarPolice() {
            return noCarPolice;
        }

        public String getDtGolive() {
            return dtGolive;
        }

        public String getStUnit() {
            return stUnit;
        }

        public Integer getCdVehicleBrand() {
            return cdVehicleBrand;
        }

        public String getDescVehilceBrand() {
            return descVehilceBrand;
        }

        public String getCdVehilceType() {
            return cdVehilceType;
        }

        public String getDescVehicleType() {
            return descVehicleType;
        }

        public String getCdVehicleModel() {
            return cdVehicleModel;
        }

        public String getDescVehicleModel() {
            return descVehicleModel;
        }

        public String getCdVehicleKind() {
            return cdVehicleKind;
        }

        public String getNoChasis() {
            return noChasis;
        }

        public String getNoEngine() {
            return noEngine;
        }

        public String getCdColor() {
            return cdColor;
        }

        public String getColor() {
            return color;
        }

        public Integer getCdVehicleCategory() {
            return cdVehicleCategory;
        }

        public Integer getCdVehicleSubCate() {
            return cdVehicleSubCate;
        }

        public String getDescVehcileSubcate() {
            return descVehcileSubcate;
        }

        public String getCdSp() {
            return cdSp;
        }

        public String getDescCp() {
            return descCp;
        }

        public Double getCdSpColl() {
            return cdSpColl;
        }

        public String getArea2() {
            return area2;
        }

        public String getCreateAt() {
            return createAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public String getIsDeleted() {
            return isDeleted;
        }

        @SerializedName("updated_at")
        private String updatedAt;

        @SerializedName("is_deleted")
        private String isDeleted;


    }


}
