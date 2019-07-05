package com.example.hunter.data.remote;

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
import com.google.gson.JsonObject;

import java.io.File;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface RemoteRepository {

    Single<LoginBean> postUserLogin(String email, String password);

    Single<UserBean> getUser(Integer userId);

    Single<RegisterBean> postUserRegister(String email, String password,String nama);

    Single<BaseBean> resendOtp(Integer userId);

    Single<BaseBean> sendOtp(Integer userId,String otp);

    Single<ProductBean> getListProduct();

    Single<Historyreportbean> getHistoryReport(Integer userId);

    Single<HistoryRedeembean> getHistoryReedem(Integer userId);

    Single<AnnouncementBean> getListAnnouncement();

    Single<ResetPasswordBean> postResetPassword(String email);

    Single<BaseBean> sendOtpForgot(Integer userId,String otp);

    Single<BaseBean> changePassword(String userId,String pass);

    Single<EditUserBean> editUser(File fileProfile, File fileKtp, JsonObject jsonObject);

    Single<OcrBean> uploadOcr(File file,Integer userId);

    Single<VehicleReportBean> vehilceReport(Integer userId, Integer vehilceId,String noPolice);

    Single<VehicleReportBean> vehilceEditHandler(Integer userId, Integer vehilceId);

    Single<ResultRedeem> redeemProduct(Integer userId, Integer productId);

    Single<OcrBean> getVehilceByLicense(String vehicle);

    Single<EditUserBean> updateImage(File image, String type,Integer user_id);

    Single<BaseBean> ajukanPerpanjangan(Integer userId, Integer vehicleId);

    Single<ProvinceBean> listProvince(String type,Integer id);

}