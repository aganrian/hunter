package com.example.hunter.data.remote;

import android.util.Log;

import com.androidnetworking.common.Priority;
import com.example.hunter.data.local.preference.PreferenceRepository;
import com.example.hunter.data.remote.bean.AnnouncementBean;
import com.example.hunter.data.remote.bean.BaseBean;
import com.example.hunter.data.remote.bean.EditUserBean;
import com.example.hunter.data.remote.bean.HistoryRedeembean;
import com.example.hunter.data.remote.bean.Historyreportbean;
import com.example.hunter.data.remote.bean.LoginBean;
import com.example.hunter.data.remote.bean.OcrBean;
import com.example.hunter.data.remote.bean.ProductBean;
import com.example.hunter.data.remote.bean.ProvinceBean;
import com.example.hunter.data.remote.bean.RegisterBean;
import com.example.hunter.data.remote.bean.ResetPasswordBean;
import com.example.hunter.data.remote.bean.ResultRedeem;
import com.example.hunter.data.remote.bean.UserBean;
import com.example.hunter.data.remote.bean.VehicleReportBean;
import com.example.hunter.utils.ImageUtils;
import com.example.hunter.utils.constant.S;
import com.google.gson.JsonObject;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.io.File;

import javax.inject.Inject;

import id.oase.indonesia.oasebrdiepa.BuildConfig;
import io.reactivex.Completable;
import io.reactivex.Single;

public class RemoteDataSource implements RemoteRepository {

    private PreferenceRepository preferenceRepo;

    @Inject
    RemoteDataSource(PreferenceRepository preferenceRepo) {
        this.preferenceRepo = preferenceRepo;
    }

    @Override
    public Single<LoginBean> postUserLogin(String email, String password) {
        return Rx2AndroidNetworking.post(BuildConfig.API_URL + "users/login?email={email}&password={password}")
                .addPathParameter("email",email)
                .addPathParameter("password",password)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(LoginBean.class);
    }

    @Override
    public Single<UserBean> getUser(Integer user_id) {
        return Rx2AndroidNetworking.get(BuildConfig.API_URL + "users?user_id={user_id}")
                .addPathParameter("user_id",String.valueOf(user_id))
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(UserBean.class);
    }

    @Override
    public Single<RegisterBean> postUserRegister(String email, String password,String nama_lengkap) {
        return Rx2AndroidNetworking.post(BuildConfig.API_URL + "users?email={email}&password={password}&nama_lengkap={nama_lengkap}")
                .addPathParameter("email",email)
                .addPathParameter("password",password)
                 .addPathParameter("nama_lengkap",nama_lengkap)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(RegisterBean.class);
    }

    @Override
    public Single<BaseBean> resendOtp(Integer userId) {
        return Rx2AndroidNetworking.post(BuildConfig.API_URL + "users/otpResend?user_id={user_id}")
                .addPathParameter("user_id",String.valueOf(userId))
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(BaseBean.class);
    }

    @Override
    public Single<BaseBean> sendOtp(Integer userId, String otp) {
        return Rx2AndroidNetworking.post(BuildConfig.API_URL + "users/otp?user_id={user_id}&otp={otp}")
                .addPathParameter("user_id",String.valueOf(userId))
                .addPathParameter("otp",otp)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(BaseBean.class);
    }

    @Override
    public Single<ProductBean> getListProduct() {
        return Rx2AndroidNetworking.get(BuildConfig.API_URL + "products?")
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(ProductBean.class);
    }

    @Override
    public Single<Historyreportbean> getHistoryReport(Integer userId) {
        return Rx2AndroidNetworking.get(BuildConfig.API_URL + "users/reportHistory?user_id={user_id}")
                .addPathParameter("user_id",String.valueOf(userId))
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(Historyreportbean.class);
    }

    @Override
    public Single<HistoryRedeembean> getHistoryReedem(Integer userId) {
        return Rx2AndroidNetworking.get(BuildConfig.API_URL + "users/redeemHistory?user_id={user_id}")
                .addPathParameter("user_id",String.valueOf(userId))
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(HistoryRedeembean.class);
    }

    @Override
    public Single<AnnouncementBean> getListAnnouncement() {
        return Rx2AndroidNetworking.get(BuildConfig.API_URL + "announcements?")
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(AnnouncementBean.class);
    }

    @Override
    public Single<ResetPasswordBean> postResetPassword(String email) {
        return Rx2AndroidNetworking.post(BuildConfig.API_URL + "users/resetPassword?email={email}")
                .addPathParameter("email",email)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(ResetPasswordBean.class);
    }

    @Override
    public Single<BaseBean> sendOtpForgot(Integer userId, String otp) {
        return Rx2AndroidNetworking.post(BuildConfig.API_URL + "users/otpResetPassword?user_id={user_id}&otp={otp}")
                .addPathParameter("user_id",String.valueOf(userId))
                .addPathParameter("otp",otp)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(BaseBean.class);
    }

    @Override
    public Single<BaseBean> changePassword(String userId, String pass) {
        return Rx2AndroidNetworking.patch(BuildConfig.API_URL + "users?user_id={user_id}&password={pass}")
                .addPathParameter("user_id",userId)
                .addPathParameter("password",pass)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(BaseBean.class);
    }

    @Override
    public Single<EditUserBean> editUser(File imageFile, File fileKtp, JsonObject jsonObject) {
        if (imageFile != null) {
            return Rx2AndroidNetworking.upload(BuildConfig.API_URL + "users/editUser?")
                    .addMultipartFile("image", imageFile)
                    .addMultipartFile("ktp_image", fileKtp)
                    .addMultipartParameter(jsonObject)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getObjectSingle(EditUserBean.class);
        }

        return Rx2AndroidNetworking.upload(BuildConfig.API_URL + "users/editUser?")
                .addMultipartParameter(jsonObject)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(EditUserBean.class);
    }

    @Override
    public Single<OcrBean> uploadOcr(File file,Integer user_id) {
        return Rx2AndroidNetworking.upload(BuildConfig.API_URL + "vehicles/ocr?user_id={user_id}")
                .addMultipartFile("image", file)
                .addPathParameter("user_id",String.valueOf(user_id))
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(OcrBean.class);
    }

    @Override
    public Single<VehicleReportBean> vehilceReport(Integer userId, Integer vehicle_id,String no_polisi) {
        if(vehicle_id==null){
            return Rx2AndroidNetworking.post(BuildConfig.API_URL + "vehicles/report?user_id={user_id}&no_polisi={no_polisi}")
                    .addPathParameter("user_id",String.valueOf(userId))
                    .addPathParameter("no_polisi",no_polisi)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getObjectSingle(VehicleReportBean.class);
        }

        return Rx2AndroidNetworking.post(BuildConfig.API_URL + "vehicles/report?user_id={user_id}&vehicle_id={vehicle_id}&no_polisi={no_polisi}")
                .addPathParameter("user_id",String.valueOf(userId))
                .addPathParameter("vehicle_id",String.valueOf(vehicle_id))
                .addPathParameter("no_polisi",no_polisi)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(VehicleReportBean.class);
    }

    @Override
    public Single<VehicleReportBean> vehilceEditHandler(Integer userId, Integer vehicle_id) {
        return Rx2AndroidNetworking.post(BuildConfig.API_URL + "vehicles/editHandler?user_id={user_id}&vehicle_id={vehicle_id}")
                .addPathParameter("user_id",String.valueOf(userId))
                .addPathParameter("vehicle_id",String.valueOf(vehicle_id))
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(VehicleReportBean.class);
    }

    @Override
    public Single<ResultRedeem> redeemProduct(Integer user_id, Integer product_id) {
        return Rx2AndroidNetworking.post(BuildConfig.API_URL + "products/redeem?product_id={product_id}&user_id={user_id}")
                .addPathParameter("product_id",String.valueOf(product_id))
                .addPathParameter("user_id",String.valueOf(user_id))
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(ResultRedeem.class);
    }

    @Override
    public Single<OcrBean> getVehilceByLicense(String license_number) {
        return Rx2AndroidNetworking.get(BuildConfig.API_URL + "vehicles/getByLicense?license_number={license_number}")
                .addPathParameter("license_number",license_number)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(OcrBean.class);
    }

    @Override
    public Single<EditUserBean> updateImage(File image, String type,Integer user_id) {
        if (type.equalsIgnoreCase(S.PROFILE)) {
            return Rx2AndroidNetworking.upload(BuildConfig.API_URL + "users/editUser?user_id={user_id}")
                    .addMultipartFile("image", image)
                    .addPathParameter("user_id",String.valueOf(user_id))
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getObjectSingle(EditUserBean.class);
        }else{
            return Rx2AndroidNetworking.upload(BuildConfig.API_URL + "users/editUser?user_id={user_id}")
                    .addMultipartFile("ktp_image", image)
                    .addPathParameter("user_id",String.valueOf(user_id))
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getObjectSingle(EditUserBean.class);
        }
    }

    @Override
    public Single<BaseBean> ajukanPerpanjangan(Integer user_id, Integer vehicle_id) {
        return Rx2AndroidNetworking.post(BuildConfig.API_URL + "vehicles/requestExtendHandle?user_id={user_id}&vehicle_id={vehicle_id}")
                .addPathParameter("user_id",String.valueOf(user_id))
                .addPathParameter("vehicle_id",String.valueOf(vehicle_id))
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(BaseBean.class);
    }

    @Override
    public Single<ProvinceBean> listProvince(String type,Integer id) {
        if(type==null || type.equalsIgnoreCase("")){
            return Rx2AndroidNetworking.get(BuildConfig.API_URL + "provinces")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getObjectSingle(ProvinceBean.class);
        }else if(type.equalsIgnoreCase(S.REGENCY)){
            return Rx2AndroidNetworking.get(BuildConfig.API_URL + "regencies?province_id={province_id}")
                    .setPriority(Priority.MEDIUM)
                    .addPathParameter("province_id",String.valueOf(id))
                    .build()
                    .getObjectSingle(ProvinceBean.class);
        }else if(type.equalsIgnoreCase(S.DISTRICT)){
            return Rx2AndroidNetworking.get(BuildConfig.API_URL + "districts?regency_id={regency_id}")
                    .setPriority(Priority.MEDIUM)
                    .addPathParameter("regency_id",String.valueOf(id))
                    .build()
                    .getObjectSingle(ProvinceBean.class);
        }else if(type.equalsIgnoreCase(S.VILLAGE)){
            return Rx2AndroidNetworking.get(BuildConfig.API_URL + "villages?district_id={district_id}")
                    .setPriority(Priority.MEDIUM)
                    .addPathParameter("district_id",String.valueOf(id))
                    .build()
                    .getObjectSingle(ProvinceBean.class);
        }else{
            return Rx2AndroidNetworking.get(BuildConfig.API_URL + "postcodes?village_id={village_id}")
                    .setPriority(Priority.MEDIUM)
                    .addPathParameter("village_id",String.valueOf(id))
                    .build()
                    .getObjectSingle(ProvinceBean.class);
        }

    }
}


