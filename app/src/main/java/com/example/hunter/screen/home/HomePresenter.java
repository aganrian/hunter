package com.example.hunter.screen.home;

import android.app.Application;

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

import id.oase.indonesia.oasebrdiepa.R;
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
}