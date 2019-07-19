package com.example.hunter.screen.historyredeem;

import android.app.Application;
import android.util.Log;

import androidx.annotation.Nullable;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.example.hunter.data.local.db.HunterDatabase;
import com.example.hunter.data.local.preference.PreferenceRepository;
import com.example.hunter.data.remote.RemoteRepository;
import com.example.hunter.screen.historyreport.HistoryReportContract;
import com.google.gson.Gson;

import org.json.JSONObject;

import javax.inject.Inject;

import com.example.hunter.R;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class HistoryRedeemPresenter implements HistoryRedeemContract.Presenter {

    private static final String TAG = HistoryRedeemPresenter.class.getSimpleName();

    private PreferenceRepository preferenceRepo;
    private Application application;
    private RemoteRepository remoteRepository;
    private HunterDatabase oaseDatabase;


    private CompositeDisposable compositeDisposable;

    @Nullable
    private HistoryRedeemContract.View mView;

    @Inject
    HistoryRedeemPresenter(Application application, RemoteRepository remoteRepository,
                           PreferenceRepository preferenceRepo, HunterDatabase oaseDatabase) {
        this.preferenceRepo = preferenceRepo;
        this.application = application;
        this.remoteRepository = remoteRepository;
        this.oaseDatabase = oaseDatabase;

        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void takeView(HistoryRedeemContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
        compositeDisposable.clear();
    }

    @Override
    public void getListHistory() {
        if(mView==null){
            return;
        }


        compositeDisposable.add(
                Completable.fromAction(() -> {
                    Integer userName = oaseDatabase.userDao().getUserId();

                    compositeDisposable.add(remoteRepository.getHistoryReedem(userName)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(reponse -> {
                                mView.setListHistory(reponse.getDataList());
                            }, error -> {

                                ANError anError = (ANError) error;
                                String json = new Gson().toJson(anError);

                                Log.e("ini",json);

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

                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe()

        );

    }
}