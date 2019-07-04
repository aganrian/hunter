package com.example.hunter.screen.fotoplat;

import android.app.Application;
import android.media.Image;
import android.util.Log;

import androidx.annotation.Nullable;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.example.hunter.data.local.db.HunterDatabase;
import com.example.hunter.data.local.db.entity.UserEntity;
import com.example.hunter.data.local.preference.PreferenceRepository;
import com.example.hunter.data.remote.RemoteRepository;
import com.example.hunter.screen.login.LoginContract;
import com.example.hunter.utils.ImageUtils;
import com.example.hunter.utils.StringUtils;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;

import javax.inject.Inject;

import id.oase.indonesia.oasebrdiepa.R;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class FotoPlatPresenter implements FotoPlatContract.Presenter {

    private static final String TAG = FotoPlatPresenter.class.getSimpleName();

    private PreferenceRepository preferenceRepo;
    private Application application;
    private RemoteRepository remoteRepository;
    private HunterDatabase oaseDatabase;


    private CompositeDisposable compositeDisposable;

    @Nullable
    private FotoPlatContract.View mView;

    @Inject
    FotoPlatPresenter(Application application, RemoteRepository remoteRepository,
                      PreferenceRepository preferenceRepo, HunterDatabase oaseDatabase) {
        this.preferenceRepo = preferenceRepo;
        this.application = application;
        this.remoteRepository = remoteRepository;
        this.oaseDatabase = oaseDatabase;

        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void takeView(FotoPlatContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
        compositeDisposable.clear();
    }

    @Override
    public void kirimOcr(File file) {
        if(mView==null){
            return;
        }
        File compressFile;
        if (file != null) {
            compressFile = ImageUtils.compressImageFile(application, file);
        } else {
            compressFile = null;
        }

        if(compressFile==null){
            mView.showErrorMessage(application.getString(R.string.filenotinserted));
            return;
        }

        mView.setLoadingIndicator(true);

        compositeDisposable.add(
                Completable.fromAction(() -> {
                    Integer usrId = oaseDatabase.userDao().getUserId();

                    compositeDisposable.add(remoteRepository.uploadOcr(compressFile,usrId)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(response -> {
                                mView.setLoadingIndicator(false);
                                mView.gotoSummary(response.getStatus(),response.getData());
                            }, error -> {
                                mView.setLoadingIndicator(false);
                                mView.showInputManual();
                            })
                    );

                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe()

        );
    }

    @Override
    public void kirimManual(String noPolisi) {
        if(mView==null){
            return;
        }

        if(noPolisi.equalsIgnoreCase("")){
            mView.showErrorMessage(application.getString(R.string.fillNopolisiManual));
            return;
        }


        mView.setLoadingIndicator(true);
        compositeDisposable.add(remoteRepository.getVehilceByLicense(noPolisi)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(reponse -> {
                    Log.e("ini balikannya", new Gson().toJson(reponse));
                    mView.setLoadingIndicator(false);
                    mView.gotoSummary(reponse.getStatus(),reponse.getData());

                }, error -> {
                    mView.setLoadingIndicator(false);
                    ANError anError = (ANError) error;
                    Log.e("ini balikannya", new Gson().toJson(anError));
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