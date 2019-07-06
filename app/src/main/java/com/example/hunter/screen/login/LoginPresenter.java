package com.example.hunter.screen.login;

import android.app.Application;

import androidx.annotation.Nullable;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.example.hunter.data.local.db.HunterDatabase;
import com.example.hunter.data.local.db.entity.UserEntity;
import com.example.hunter.data.local.preference.PreferenceRepository;
import com.example.hunter.data.remote.RemoteRepository;
import com.example.hunter.utils.StringUtils;

import org.json.JSONObject;

import javax.inject.Inject;

import id.oase.indonesia.oasebrdiepa.R;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter implements LoginContract.Presenter {

    private static final String TAG = LoginPresenter.class.getSimpleName();

    private PreferenceRepository preferenceRepo;
    private Application application;
    private RemoteRepository remoteRepository;
    private HunterDatabase oaseDatabase;


    private CompositeDisposable compositeDisposable;

    @Nullable
    private LoginContract.View mView;

    @Inject
    LoginPresenter(Application application, RemoteRepository remoteRepository,
                   PreferenceRepository preferenceRepo,HunterDatabase oaseDatabase) {
        this.preferenceRepo = preferenceRepo;
        this.application = application;
        this.remoteRepository = remoteRepository;
        this.oaseDatabase = oaseDatabase;

        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void takeView(LoginContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
        compositeDisposable.clear();
    }

    @Override
    public void doLogin(String phone, String password) {

        if (mView == null) {
            return;
        }

        if(phone.equalsIgnoreCase("")){
            mView.showErrorMessage(application.getString(R.string.err_message_nohandphone));
            return;
        }

        if(!StringUtils.isValidEmailId(phone)){
            mView.showErrorMessage(application.getString(R.string.err_message_emailnotvalid));
            return;
        }

        if(password.equalsIgnoreCase("")){
            mView.showErrorMessage(application.getString(R.string.err_message_password));
            return;
        }

        mView.setLoadingIndicator(true);
        compositeDisposable.add(remoteRepository.postUserLogin(phone,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(reponse -> {
                    mView.setLoadingIndicator(false);

                        UserEntity userEntity = new UserEntity();
                        userEntity.setUserId(reponse.getId_user());
                        userEntity.setAlamat(reponse.getAlamat());
                        userEntity.setCreated_at(reponse.getCreated_at());
                        userEntity.setDate_login(reponse.getDate_login());
                        userEntity.setDate_register(reponse.getDate_register());
                        userEntity.setEmail(reponse.getEmail());
                        userEntity.setFlag_active(reponse.getFlag_active());
                        userEntity.setHidden_otp(reponse.getHidden_otp());
                        userEntity.setIs_deleted(reponse.getIs_deleted());
                        userEntity.setNama_lengkap(reponse.getNama_lengkap());
                        userEntity.setNo_hp(reponse.getNo_hp());
                        userEntity.setNo_ktp(reponse.getNo_ktp());
                        userEntity.setOtp(reponse.getOtp());
                        userEntity.setPicture(reponse.getPicture());
                        userEntity.setPoint(reponse.getPoint());
                        userEntity.setRole(reponse.getRole());
                        userEntity.setUpdated_at(reponse.getUpdated_at());
                        userEntity.setKtp_picture(reponse.getKtp_picture());
                        userEntity.setTgl_lahir(reponse.getTgl_lahir());
                        userEntity.setGender(reponse.getGender());
                        userEntity.setProvinsi(reponse.getProvinsi());
                        userEntity.setKabupaten(reponse.getKabupaten());
                        userEntity.setKecamatan(reponse.getKecamatan());
                        userEntity.setKelurahan(reponse.getKelurahan());
                        userEntity.setKodepos(reponse.getKodepos());
                        userEntity.setKabupaten_id(reponse.getKabupaten_id());
                        userEntity.setKecamatan_id(reponse.getKecamatan_id());
                        userEntity.setProvinsi_id(reponse.getProvinsi_id());
                        userEntity.setKelurahan_id(reponse.getKelurahan_id());
                        userEntity.setKodepos_id(reponse.getKodepos_id());


                    preferenceRepo.setUserLogged(true);
                        compositeDisposable.add(Completable.fromAction(() -> {
                                    oaseDatabase.userDao().deleteUser();
                                    oaseDatabase.userDao().insertUser(userEntity);
                                }).subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe()
                        );

                        mView.gotoHomeActivity();


                }, error -> {
                    mView.setLoadingIndicator(false);

                    ANError anError = (ANError) error;
                    if (anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)) {
                        mView.showErrorMessage(application.getString(R.string.message_connection_lost));
                    } else {
                        if (anError.getErrorBody() != null) {
                            JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                            mView.showErrorMessage(jsonObject.optString("status"));
                        }
                    }
                })
        );

    }
}