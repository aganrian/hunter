package com.example.hunter.screen.main;

import android.app.Application;

import androidx.annotation.Nullable;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.example.hunter.data.local.db.HunterDatabase;
import com.example.hunter.data.local.preference.PreferenceRepository;
import com.example.hunter.data.remote.RemoteRepository;
import com.google.gson.Gson;

import org.json.JSONObject;

import javax.inject.Inject;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter implements MainContract.Presenter {

    private Application application;
    private RemoteRepository remoteRepo;
    private HunterDatabase oaseDatabase;
    private PreferenceRepository preferenceRepository;

    private CompositeDisposable mDisposables;

    @Nullable
    private MainContract.View mView;

    @Inject
    MainPresenter(HunterDatabase oaseDatabase, Application application, RemoteRepository remoteRepo, PreferenceRepository preferenceRepository) {
        this.application = application;
        this.remoteRepo = remoteRepo;
        this.oaseDatabase = oaseDatabase;
        this.preferenceRepository = preferenceRepository;

        mDisposables = new CompositeDisposable();
    }

    @Override
    public void takeView(MainContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
        mDisposables.clear();
    }


}
