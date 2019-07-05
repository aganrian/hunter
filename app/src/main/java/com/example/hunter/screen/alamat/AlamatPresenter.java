package com.example.hunter.screen.alamat;

import android.app.Application;
import android.util.Log;

import androidx.annotation.Nullable;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.example.hunter.data.local.db.HunterDatabase;
import com.example.hunter.data.local.db.entity.UserEntity;
import com.example.hunter.data.local.preference.PreferenceRepository;
import com.example.hunter.data.remote.RemoteRepository;
import com.example.hunter.data.remote.bean.EditUserBean;
import com.example.hunter.screen.login.LoginContract;
import com.example.hunter.utils.StringUtils;
import com.example.hunter.utils.TimeUtils;
import com.example.hunter.utils.constant.S;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.List;

import javax.inject.Inject;

import id.oase.indonesia.oasebrdiepa.R;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class AlamatPresenter implements AlamatContract.Presenter {

    private static final String TAG = AlamatPresenter.class.getSimpleName();

    private PreferenceRepository preferenceRepo;
    private Application application;
    private RemoteRepository remoteRepository;
    private HunterDatabase hunterDatabase;


    private CompositeDisposable compositeDisposable;

    @Nullable
    private AlamatContract.View mView;

    @Inject
    AlamatPresenter(Application application, RemoteRepository remoteRepository,
                    PreferenceRepository preferenceRepo, HunterDatabase oaseDatabase) {
        this.preferenceRepo = preferenceRepo;
        this.application = application;
        this.remoteRepository = remoteRepository;
        this.hunterDatabase = oaseDatabase;

        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void takeView(AlamatContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
        compositeDisposable.clear();
    }

    @Override
    public void getAlamat() {
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

                    mView.setAlamat(list.get(0));
                })
        );


    }


    @Override
    public void getProvinsi() {
        if(mView==null){
            return;
        }

        mView.setLoadingIndicator(true);
        compositeDisposable.add(remoteRepository.listProvince(null,null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(reponse -> {
                    mView.setLoadingIndicator(false);
                    mView.setProvinsi(reponse.getDataList());
                }, error -> {
                    mView.setLoadingIndicator(false);
                    ANError anError = (ANError) error;
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

    @Override
    public void getKabupaten(Integer provinceId) {
        if(mView==null){
            return;
        }

        mView.setLoadingIndicator(true);
        compositeDisposable.add(remoteRepository.listProvince(S.REGENCY,provinceId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(reponse -> {
                    mView.setLoadingIndicator(false);
                    mView.setKabupaten(reponse.getDataList());
                }, error -> {
                    mView.setLoadingIndicator(false);
                    ANError anError = (ANError) error;
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

    @Override
    public void getDistrict(Integer regencyId) {
        if(mView==null){
            return;
        }

        mView.setLoadingIndicator(true);
        compositeDisposable.add(remoteRepository.listProvince(S.DISTRICT,regencyId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(reponse -> {
                    mView.setLoadingIndicator(false);
                    mView.setKecamatan(reponse.getDataList());
                }, error -> {
                    mView.setLoadingIndicator(false);
                    ANError anError = (ANError) error;
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

    @Override
    public void getVillage(Integer districtId) {
        if(mView==null){
            return;
        }

        mView.setLoadingIndicator(true);
        compositeDisposable.add(remoteRepository.listProvince(S.VILLAGE,districtId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(reponse -> {
                    mView.setLoadingIndicator(false);
                    mView.setKelurahan(reponse.getDataList());
                }, error -> {
                    mView.setLoadingIndicator(false);
                    ANError anError = (ANError) error;
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

    @Override
    public void getPostalCode(Integer villageId) {
        if(mView==null){
            return;
        }

        mView.setLoadingIndicator(true);
        compositeDisposable.add(remoteRepository.listProvince(S.POSTCODE,villageId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(reponse -> {
                    mView.setLoadingIndicator(false);
                    mView.setKodePost(reponse.getDataList());
                }, error -> {
                    mView.setLoadingIndicator(false);
                    ANError anError = (ANError) error;
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

    @Override
    public void simpanPerubahan(String province, Integer provinceId, String kabupaten, Integer kabupatenId, String kecamatan, Integer kecamatanId, String kelurahan, Integer kelurahanId, String postcode, Integer postcodeId, String alamat) {
        if(mView==null){
            return;
        }

        if(province.equalsIgnoreCase("")|| provinceId==0 || kabupaten.equalsIgnoreCase("") ||
            kabupatenId==0 ||kecamatan.equalsIgnoreCase("")||kecamatanId==0 || kelurahan.equalsIgnoreCase("")||
            kelurahanId==0 || postcode.equalsIgnoreCase("") || postcodeId==0){
            mView.showErrorMessage(application.getString(R.string.fillData));
            return;
        }

        if(alamat.equalsIgnoreCase("")){
            mView.showErrorMessage(application.getString(R.string.fillDataAlamat));
            return;
        }

        mView.setLoadingIndicator(true);
        compositeDisposable.add(
                Completable.fromAction(() -> {
                    Integer userName = hunterDatabase.userDao().getUserId();

                    JsonObject json = new JsonObject();
                    json.addProperty("user_id", userName);
                    json.addProperty("provinsi_id", provinceId);
                    json.addProperty("kabupaten_id", kabupatenId);
                    json.addProperty("kecamatan_id", kecamatanId);
                    json.addProperty("kelurahan_id",kelurahanId);
                    json.addProperty("kodepos_id",postcodeId);
                    json.addProperty("provinsi",province);
                    json.addProperty("kabupaten",kabupaten);
                    json.addProperty("kecamatan",kecamatan);
                    json.addProperty("kelurahan",kelurahan);
                    json.addProperty("kodepos",postcode);
                    json.addProperty("alamat",alamat);



                    compositeDisposable.add(remoteRepository.editUser(null,null, json)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(response -> {
                                mView.setLoadingIndicator(false);
                                getUser(response);
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

    private void getUser(EditUserBean reponse) {
        if(mView==null){
            return;
        }

        mView.setLoadingIndicator(false);

        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(reponse.getLoginBean().getId_user());
        userEntity.setAlamat(reponse.getLoginBean().getAlamat());
        userEntity.setCreated_at(reponse.getLoginBean().getCreated_at());
        userEntity.setDate_login(reponse.getLoginBean().getDate_login());
        userEntity.setDate_register(reponse.getLoginBean().getDate_register());
        userEntity.setEmail(reponse.getLoginBean().getEmail());
        userEntity.setFlag_active(reponse.getLoginBean().getFlag_active());
        userEntity.setHidden_otp(reponse.getLoginBean().getHidden_otp());
        userEntity.setIs_deleted(reponse.getLoginBean().getIs_deleted());
        userEntity.setNama_lengkap(reponse.getLoginBean().getNama_lengkap());
        userEntity.setNo_hp(reponse.getLoginBean().getNo_hp());
        userEntity.setNo_ktp(reponse.getLoginBean().getNo_ktp());
        userEntity.setOtp(reponse.getLoginBean().getOtp());
        userEntity.setPicture(reponse.getLoginBean().getPicture());
        userEntity.setPoint(reponse.getLoginBean().getPoint());
        userEntity.setRole(reponse.getLoginBean().getRole());
        userEntity.setUpdated_at(reponse.getLoginBean().getUpdated_at());
        userEntity.setKtp_picture(reponse.getLoginBean().getKtp_picture());
        userEntity.setTgl_lahir(reponse.getLoginBean().getTgl_lahir());
        userEntity.setGender(reponse.getLoginBean().getGender());
        userEntity.setProvinsi(reponse.getLoginBean().getProvinsi());
        userEntity.setKabupaten(reponse.getLoginBean().getKabupaten());
        userEntity.setKecamatan(reponse.getLoginBean().getKecamatan());
        userEntity.setKelurahan(reponse.getLoginBean().getKelurahan());
        userEntity.setKodepos(reponse.getLoginBean().getKodepos());
        userEntity.setKabupaten_id(reponse.getLoginBean().getKabupaten_id());
        userEntity.setKecamatan_id(reponse.getLoginBean().getKecamatan_id());
        userEntity.setProvinsi_id(reponse.getLoginBean().getProvinsi_id());
        userEntity.setKelurahan_id(reponse.getLoginBean().getKelurahan_id());
        userEntity.setKodepos_id(reponse.getLoginBean().getKodepos_id());


        preferenceRepo.setUserLogged(true);
        compositeDisposable.add(Completable.fromAction(() -> {
                    hunterDatabase.userDao().deleteUser();
                    hunterDatabase.userDao().insertUser(userEntity);
                mView.setPerubahan();
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe()
        );



    }

}