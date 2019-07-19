package com.example.hunter.screen.home;

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
import com.google.gson.Gson;

import org.json.JSONObject;

import javax.inject.Inject;

import com.example.hunter.R;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class HomePresenter implements HomeContract.Presenter {

    private static final String TAG = HomePresenter.class.getSimpleName();

    private PreferenceRepository preferenceRepo;
    private Application application;
    private RemoteRepository remoteRepository;
    private HunterDatabase oaseDatabase;


    private CompositeDisposable compositeDisposable;

    @Nullable
    private HomeContract.View mView;

    @Inject
    HomePresenter(Application application, RemoteRepository remoteRepository,
                  PreferenceRepository preferenceRepo, HunterDatabase oaseDatabase) {
        this.preferenceRepo = preferenceRepo;
        this.application = application;
        this.remoteRepository = remoteRepository;
        this.oaseDatabase = oaseDatabase;

        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void takeView(HomeContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
        compositeDisposable.clear();
    }

    /*function metode untuk mengambil data user dari sql lite*/
    @Override
    public void getHomeInformation() {
        if(mView==null){
            return;
        }

        compositeDisposable.add(
                Completable.fromAction(() -> {
                    mView.setLoadingIndicator(true);
                    String logoProfile = oaseDatabase.userDao().getPicture();
                    String name = oaseDatabase.userDao().getName();
                    Integer point = oaseDatabase.userDao().getPoint();
                    mView.setHomeInformation(logoProfile,name,point);

                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe()

        );
    }

    /*function untuk memanggil list product dari API ketika swipe*/
    @Override
    public void getProductAllRefreseh() {
        if(mView==null){
            return;
        }

        compositeDisposable.add(remoteRepository.getListProduct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(reponse -> {
                    mView.setListProductRefresh(reponse.getDataList());
                }, error -> {

                    ANError anError = (ANError) error;
                    String json = new Gson().toJson(anError);

                    if (anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)) {
                        mView.showErrorMessage(application.getString(R.string.message_connection_lost));
                    } else {
                        if (anError.getErrorBody() != null) {
                            JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                            mView.showErrorMessage(jsonObject.optString("message"));
                        }
                    }
                    //ini ketika gagal ambil data dy harus login lagi dan menghapus data user serta profule yang telah berhasil login

                })
        );
    }

    /*function untuk memanggil list product dari API ketika not swipe*/
    @Override
    public void getProductAll() {
        if(mView==null){
            return;
        }

        compositeDisposable.add(remoteRepository.getListProduct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(reponse -> {
                    mView.setListProduct(reponse.getDataList());
                }, error -> {

                    ANError anError = (ANError) error;
                    String json = new Gson().toJson(anError);

                    if (anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)) {
                        mView.showErrorMessage(application.getString(R.string.message_connection_lost));
                    } else {
                        if (anError.getErrorBody() != null) {
                            JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                            mView.showErrorMessage(jsonObject.optString("message"));
                        }
                    }
                    //ini ketika gagal ambil data dy harus login lagi dan menghapus data user serta profule yang telah berhasil login

                })
        );
    }

    /*api untuk melaukan product reedem dari list*/
    @Override
    public void productReadem(Integer productId) {
        if(mView==null){
            return;
        }



        mView.setLoadingIndicator(true);
        compositeDisposable.add(
                Completable.fromAction(() -> {
                    mView.setLoadingIndicator(true);
                    Integer userId = oaseDatabase.userDao().getUserId();

                    compositeDisposable.add(remoteRepository.redeemProduct(userId,productId)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(reponse -> {
                                //ketika berhasil akan update data point dari user
                                updateUserData(reponse.getPoint());
                            }, error -> {
                                mView.setLoadingIndicator(false);
                                ANError anError = (ANError) error;
                                if (anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)) {
                                    mView.showErrorMessage(application.getString(R.string.message_connection_lost));
                                } else {
                                    if (anError.getErrorBody() != null) {
                                        JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                                        mView.showErrorMessage(jsonObject.optString("message"));
                                    }
                                }
                            })
                    );

                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe()

        );
    }

    //update data user untuk point
    private void updateUserData(Integer pointChange){
        if(mView==null){
            return;
        }

        compositeDisposable.add(
                Completable.fromAction(() -> {
                    mView.setLoadingIndicator(false);
                    Integer userName = oaseDatabase.userDao().getUserId();
                    compositeDisposable.add(Completable.fromAction(() -> {
                                oaseDatabase.userDao().updateJumlahHasil(pointChange,userName);
                                mView.updatePoint(String.valueOf(pointChange));
                            }).subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe()
                    );

                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe()

        );
    }

    @Override
    public void getUser() {
        if(mView==null){
            return;
        }

        compositeDisposable.add(
                Completable.fromAction(() -> {
                    Integer userName = oaseDatabase.userDao().getUserId();

                    compositeDisposable.add(remoteRepository.getUser(userName)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(reponse -> {
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
                                userEntity.setKabupaten_id(reponse.getData().getKabupaten_id());
                                userEntity.setKecamatan_id(reponse.getData().getKecamatan_id());
                                userEntity.setProvinsi_id(reponse.getData().getProvinsi_id());
                                userEntity.setKelurahan_id(reponse.getData().getKelurahan_id());
                                userEntity.setKodepos_id(reponse.getData().getKodepos_id());


                                preferenceRepo.setUserLogged(true);
                                compositeDisposable.add(Completable.fromAction(() -> {
                                            oaseDatabase.userDao().deleteUser();
                                            oaseDatabase.userDao().insertUser(userEntity);
                                        }).subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe()
                                );

                                mView.setHomeInformationRefresh(reponse);


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
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe()

        );
    }
}