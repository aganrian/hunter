package com.example.hunter.screen.history;

import android.app.Application;

import androidx.annotation.Nullable;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.example.hunter.data.local.db.HunterDatabase;
import com.example.hunter.data.local.preference.PreferenceRepository;
import com.example.hunter.data.remote.RemoteRepository;
import com.example.hunter.screen.home.HomeContract;
import com.google.gson.Gson;

import org.json.JSONObject;

import javax.inject.Inject;

import com.example.hunter.R;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class HistoryPresenter implements HistoryContract.Presenter {

    private static final String TAG = HistoryPresenter.class.getSimpleName();

    private PreferenceRepository preferenceRepo;
    private Application application;
    private RemoteRepository remoteRepository;
    private HunterDatabase oaseDatabase;


    private CompositeDisposable compositeDisposable;

    @Nullable
    private HistoryContract.View mView;

    @Inject
    HistoryPresenter(Application application, RemoteRepository remoteRepository,
                     PreferenceRepository preferenceRepo, HunterDatabase oaseDatabase) {
        this.preferenceRepo = preferenceRepo;
        this.application = application;
        this.remoteRepository = remoteRepository;
        this.oaseDatabase = oaseDatabase;

        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void takeView(HistoryContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
        compositeDisposable.clear();
    }

}