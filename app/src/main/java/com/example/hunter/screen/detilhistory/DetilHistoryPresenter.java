package com.example.hunter.screen.detilhistory;

import android.app.Application;
import android.util.Log;

import androidx.annotation.Nullable;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.example.hunter.data.local.db.HunterDatabase;
import com.example.hunter.data.local.preference.PreferenceRepository;
import com.example.hunter.data.remote.RemoteRepository;
import com.example.hunter.data.remote.bean.Historyreportbean;
import com.example.hunter.screen.fotoplatreport.FotoPlatReportContract;
import com.example.hunter.utils.constant.S;
import com.google.gson.Gson;

import org.json.JSONObject;

import javax.inject.Inject;

import id.oase.indonesia.oasebrdiepa.R;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class DetilHistoryPresenter implements DetilHistoryContract.Presenter {

    private static final String TAG = DetilHistoryPresenter.class.getSimpleName();

    private PreferenceRepository preferenceRepo;
    private Application application;
    private RemoteRepository remoteRepository;
    private HunterDatabase oaseDatabase;


    private CompositeDisposable compositeDisposable;

    @Nullable
    private DetilHistoryContract.View mView;

    @Inject
    DetilHistoryPresenter(Application application, RemoteRepository remoteRepository,
                          PreferenceRepository preferenceRepo, HunterDatabase oaseDatabase) {
        this.preferenceRepo = preferenceRepo;
        this.application = application;
        this.remoteRepository = remoteRepository;
        this.oaseDatabase = oaseDatabase;

        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void takeView(DetilHistoryContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
        compositeDisposable.clear();
    }

    @Override
    public void ajukanPerpanjangan(Integer vehicleId) {
        if(mView==null){
            return;
        }

        mView.setLoadingIndicator(true);
        compositeDisposable.add(
                Completable.fromAction(() -> {
                    Integer userName = oaseDatabase.userDao().getUserId();

                    compositeDisposable.add(remoteRepository.ajukanPerpanjangan(userName,vehicleId)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(reponse -> {
                                mView.showDialogSuccess();
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
                            })
                    );

                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe()

        );
    }
}