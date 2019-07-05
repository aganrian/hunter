package com.example.hunter.data.local.db.entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class UserEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "id_user")
    private Integer userId;

    @ColumnInfo(name = "nama_lengkap")
    private String nama_lengkap;

    @ColumnInfo(name = "alamat")
    private String alamat;

    @ColumnInfo(name= "no_ktp")
    private String no_ktp;

    @ColumnInfo(name ="email")
    private String email;

    @ColumnInfo(name = "flag_active")
    private Integer flag_active;

    @ColumnInfo(name = "no_hp")
    private String no_hp;

    @ColumnInfo (name = "date_login")
    private String date_login;

    @ColumnInfo (name = "date_register")
    private String date_register;

    @ColumnInfo (name = "updated_at")
    private String updated_at;

    @ColumnInfo (name = "role")
    private Integer role;

    @ColumnInfo (name = "created_at")
    private String created_at;

    @ColumnInfo (name = "otp")
    private String otp;

    @ColumnInfo (name = "is_deleted")
    private Integer is_deleted;

    @ColumnInfo (name = "hidden_otp")
    private String hidden_otp;

    @ColumnInfo (name = "picture")
    private String picture;

    @ColumnInfo (name = "ktp_picture")
    private String ktp_picture;

    @ColumnInfo (name = "point")
    private Integer point;

    @ColumnInfo (name = "tgl_lahir")
    private String tgl_lahir;

    @ColumnInfo (name = "gender")
    private String gender;

    @ColumnInfo (name = "provinsi")
    private String provinsi;

    @ColumnInfo (name = "kabupaten")
    private String kabupaten;

    @ColumnInfo (name = "kecamatan")
    private String kecamatan;

    @ColumnInfo (name = "kelurahan")
    private String kelurahan;

    @ColumnInfo (name = "kodepos")
    private String kodepos;

    @ColumnInfo (name = "provinsi_id")
    private Integer provinsi_id;

    @ColumnInfo (name = "kabupaten_id")
    private Integer kabupaten_id;

    @ColumnInfo (name = "kecamatan_id")
    private Integer kecamatan_id;

    @ColumnInfo (name = "kelurahan_id")
    private Integer kelurahan_id;

    @ColumnInfo (name = "kodepos_id")
    private Integer kodepos_id;

    public Integer getProvinsi_id() {
        return provinsi_id;
    }

    public void setProvinsi_id(Integer provinsi_id) {
        this.provinsi_id = provinsi_id;
    }

    public Integer getKabupaten_id() {
        return kabupaten_id;
    }

    public void setKabupaten_id(Integer kabupaten_id) {
        this.kabupaten_id = kabupaten_id;
    }

    public Integer getKecamatan_id() {
        return kecamatan_id;
    }

    public void setKecamatan_id(Integer kecamatan_id) {
        this.kecamatan_id = kecamatan_id;
    }

    public Integer getKelurahan_id() {
        return kelurahan_id;
    }

    public void setKelurahan_id(Integer kelurahan_id) {
        this.kelurahan_id = kelurahan_id;
    }

    public Integer getKodepos_id() {
        return kodepos_id;
    }

    public void setKodepos_id(Integer kodepos_id) {
        this.kodepos_id = kodepos_id;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public String getKabupaten() {
        return kabupaten;
    }

    public void setKabupaten(String kabupaten) {
        this.kabupaten = kabupaten;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getKelurahan() {
        return kelurahan;
    }

    public void setKelurahan(String kelurahan) {
        this.kelurahan = kelurahan;
    }

    public String getKodepos() {
        return kodepos;
    }

    public void setKodepos(String kodepos) {
        this.kodepos = kodepos;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTgl_lahir() {
        return tgl_lahir;
    }

    public void setKtp_picture(String ktp_picture) {
        this.ktp_picture = ktp_picture;
    }

    public String getKtp_picture() {
        return ktp_picture;
    }

    public void setTgl_lahir(String tgl_lahir) {
        this.tgl_lahir = tgl_lahir;
    }

    public String getGender() {
        return gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public void setNama_lengkap(String nama_lengkap) {
        this.nama_lengkap = nama_lengkap;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNo_ktp() {
        return no_ktp;
    }

    public void setNo_ktp(String no_ktp) {
        this.no_ktp = no_ktp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getFlag_active() {
        return flag_active;
    }

    public void setFlag_active(Integer flag_active) {
        this.flag_active = flag_active;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getDate_login() {
        return date_login;
    }

    public void setDate_login(String date_login) {
        this.date_login = date_login;
    }

    public String getDate_register() {
        return date_register;
    }

    public void setDate_register(String date_register) {
        this.date_register = date_register;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Integer getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(Integer is_deleted) {
        this.is_deleted = is_deleted;
    }

    public String getHidden_otp() {
        return hidden_otp;
    }

    public void setHidden_otp(String hidden_otp) {
        this.hidden_otp = hidden_otp;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }
}
