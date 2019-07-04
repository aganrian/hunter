package com.example.hunter.data.remote.bean;

import com.google.gson.annotations.SerializedName;

public class LoginBean  {

    @SerializedName("id_user")
    private Integer id_user;

    @SerializedName("nama_lengkap")
    private String nama_lengkap;

    @SerializedName("alamat")
    private String alamat;

    @SerializedName("no_ktp")
    private String no_ktp;

    @SerializedName("email")
    private String email;

    public Integer getId_user() {
        return id_user;
    }

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getNo_ktp() {
        return no_ktp;
    }

    public String getEmail() {
        return email;
    }

    public Integer getFlag_active() {
        return flag_active;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public String getDate_login() {
        return date_login;
    }

    public String getDate_register() {
        return date_register;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public Integer getRole() {
        return role;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getOtp() {
        return otp;
    }

    public Integer getIs_deleted() {
        return is_deleted;
    }

    public String getHidden_otp() {
        return hidden_otp;
    }

    public Integer getPoint() {
        return point;
    }

    public String getPicture() {
        return picture;
    }

    @SerializedName("flag_active")
    private Integer flag_active;

    @SerializedName("no_hp")
    private String no_hp;

    @SerializedName("date_login")
    private String date_login;

    @SerializedName("date_register")
    private String date_register;

    @SerializedName("updated_at")
    private String updated_at;

    @SerializedName("role")
    private Integer role;

    @SerializedName("created_at")
    private String created_at;

    @SerializedName("otp")
    private String otp;

    @SerializedName("is_deleted")
    private Integer is_deleted;

    @SerializedName("hidden_otp")
    private String hidden_otp;

    @SerializedName("point")
    private Integer point;

    @SerializedName("picture")
    private String picture;

    @SerializedName("ktp_picture")
    private String ktp_picture;

    @SerializedName("tgl_lahir")
    private String tgl_lahir;

    @SerializedName("gender")
    private String gender;

    @SerializedName("provinsi")
    private String provinsi;

    @SerializedName("kabupaten")
    private String kabupaten;

    @SerializedName("kecamatan")
    private String kecamatan;

    @SerializedName("kelurahan")
    private String kelurahan;

    @SerializedName("kodepos")
    private String kodepos;

    public String getKabupaten() {
        return kabupaten;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public String getKelurahan() {
        return kelurahan;
    }

    public String getKodepos() {
        return kodepos;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public String getGender() {
        return gender;
    }

    public String getKtp_picture() {
        return ktp_picture;
    }

    public String getTgl_lahir() {
        return tgl_lahir;
    }
}
