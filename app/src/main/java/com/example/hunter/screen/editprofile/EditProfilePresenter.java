package com.example.hunter.screen.editprofile;

import android.app.Application;
import android.util.Log;

import androidx.annotation.Nullable;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.example.hunter.data.local.db.HunterDatabase;
import com.example.hunter.data.local.db.entity.UserEntity;
import com.example.hunter.data.local.preference.PreferenceRepository;
import com.example.hunter.data.remote.RemoteRepository;
import com.example.hunter.screen.login.LoginContract;
import com.example.hunter.utils.StringUtils;
import com.example.hunter.utils.TimeUtils;
import com.example.hunter.utils.constant.S;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import id.oase.indonesia.oasebrdiepa.R;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class EditProfilePresenter implements EditProfileContract.Presenter {

    private static final String TAG = EditProfilePresenter.class.getSimpleName();

    private PreferenceRepository preferenceRepo;
    private Application application;
    private RemoteRepository remoteRepository;
    private HunterDatabase hunterDatabase;


    private CompositeDisposable compositeDisposable;

    @Nullable
    private EditProfileContract.View mView;

    @Inject
    EditProfilePresenter(Application application, RemoteRepository remoteRepository,
                         PreferenceRepository preferenceRepo, HunterDatabase oaseDatabase) {
        this.preferenceRepo = preferenceRepo;
        this.application = application;
        this.remoteRepository = remoteRepository;
        this.hunterDatabase = oaseDatabase;

        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void takeView(EditProfileContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
        compositeDisposable.clear();
    }

    @Override
    public void getDataProfile() {
        if(mView==null){
            return;
        }
        compositeDisposable.add(hunterDatabase.userDao().loadUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((List<UserEntity> list) -> {

                    if (mView == null) {
                        return;
                    }

                    mView.setDataProfile(list.get(0));
                })
        );
    }

    @Override
    public void updateImage(File imageFile, String type) {
        if(mView==null){
            return;
        }

        mView.setLoadingIndicator(true);
        compositeDisposable.add(
                Completable.fromAction(() -> {
                    Integer userName = hunterDatabase.userDao().getUserId();

                    mView.setLoadingIndicator(true);
                    compositeDisposable.add(remoteRepository.updateImage(imageFile,type,userName)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(response -> {
                                mView.setLoadingIndicator(false);
                                compositeDisposable.add(
                                        Completable.fromAction(() -> {
                                            if(type.equalsIgnoreCase(S.KTP)){
                                                hunterDatabase.userDao().updateUrlKtpProfile(response.getLoginBean().getKtp_picture(),userName);
                                            }else{
                                                hunterDatabase.userDao().updateUrlImageProfile(response.getLoginBean().getPicture(),userName);
                                            }
                                        }).subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe()

                                );

                            }, error -> {
                                mView.setLoadingIndicator(false);
                                ANError anError = (ANError) error;
                                Log.e("ini error",new Gson().toJson(anError));
                                mView.showErrorMessage(anError.getErrorDetail());
                                if (anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)) {
                                    mView.showErrorMessage(application.getString(R.string.message_connection_lost));
                                } else {
                                    if (anError.getErrorBody() != null) {
                                        mView.showErrorMessage(anError.getErrorBody());
                                    }
                                }
                            })
                    );

                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe()

        );
    }

    @Override
    public void updateData(String nama, String nomorTelepon, String tanggalLahir, String gender, String noKtp) {
        if(mView==null){
            return;
        }


        mView.setLoadingIndicator(true);
        compositeDisposable.add(
                Completable.fromAction(() -> {
                    Integer userName = hunterDatabase.userDao().getUserId();

                    JsonObject json = new JsonObject();
                    json.addProperty("user_id", userName);
                    json.addProperty("name_lengkap", nama);
                    json.addProperty("no_hp", nomorTelepon);
                    json.addProperty("updated_at", TimeUtils.getNowDate());
                    json.addProperty("tgl_lahir",tanggalLahir);
                    json.addProperty("gender",gender);
                    json.addProperty("no_ktp",noKtp);



                    compositeDisposable.add(remoteRepository.editUser(null,null, json)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(response -> {
                                mView.setLoadingIndicator(false);
                                getUser(userName);
                            }, error -> {
                                mView.setLoadingIndicator(false);
                                ANError anError = (ANError) error;
                                Log.e("ini error",new Gson().toJson(anError));
                                mView.showErrorMessage(anError.getErrorDetail());
                                if (anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)) {
                                    mView.showErrorMessage(application.getString(R.string.message_connection_lost));
                                } else {
                                    if (anError.getErrorBody() != null) {
                                        mView.showErrorMessage(anError.getErrorBody());
                                    }
                                }
                            })
                    );

                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe()

        );


    }

    private void getUser(Integer userId) {
        if(mView==null){
            return;
        }

        compositeDisposable.add(remoteRepository.getUser(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(reponse -> {
                    Log.e("ini error apa", new Gson().toJson(reponse));
                    mView.setLoadingIndicator(false);

                    UserEntity userEntity = new UserEntity();
                    userEntity.setUserId(reponse.getData().getId_user());
                    userEntity.setAlamat(reponse.getData().getAlamat());
                    userEntity.setCreated_at(reponse.getData().getCreated_at());
                    userEntity.setDate_login(reponse.getData().getDate_login());
                    userEntity.setDate_register(reponse.getData().getDate_register());
                    userEntity.setEmail(reponse.getData().getEmail());
                    userEntity.setFlag_active(reponse.getData().getFlag_active());
                    userEntity.setHidden_otp(reponse.getData().getHidden_otp());
                    userEntity.setIs_deleted(reponse.getData().getIs_deleted());
                    userEntity.setNama_lengkap(reponse.getData().getNama_lengkap());
                    userEntity.setNo_hp(reponse.getData().getNo_hp());
                    userEntity.setNo_ktp(reponse.getData().getNo_ktp());
                    userEntity.setOtp(reponse.getData().getOtp());
                    userEntity.setPicture(reponse.getData().getPicture());
                    userEntity.setPoint(reponse.getData().getPoint());
                    userEntity.setRole(reponse.getData().getRole());
                    userEntity.setUpdated_at(reponse.getData().getUpdated_at());
                    userEntity.setKtp_picture(reponse.getData().getKtp_picture());
                    userEntity.setTgl_lahir(reponse.getData().getTgl_lahir());
                    userEntity.setGender(reponse.getData().getGender());
                    userEntity.setProvinsi(reponse.getData().getProvinsi());
                    userEntity.setKabupaten(reponse.getData().getKabupaten());
                    userEntity.setKecamatan(reponse.getData().getKecamatan());
                    userEntity.setKelurahan(reponse.getData().getKelurahan());
                    userEntity.setKodepos(reponse.getData().getKodepos());

                    preferenceRepo.setUserLogged(true);
                    compositeDisposable.add(Completable.fromAction(() -> {
                                hunterDatabase.userDao().deleteUser();
                                hunterDatabase.userDao().insertUser(userEntity);
                            }).subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe()
                    );

                    mView.gotoProfile();


                }, error -> {
                    mView.setLoadingIndicator(false);

                    ANError anError = (ANError) error;
                    Log.e("ini error apa", new Gson().toJson(anError));
                    if (anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)) {
                        mView.showErrorMessage(application.getString(R.string.message_connection_lost));
                    } else {
                        if (anError.getErrorBody() != null) {
                            mView.showErrorMessage(anError.getErrorBody());
                        }
                    }
                })
        );

    }
}