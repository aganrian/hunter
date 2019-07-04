package com.example.hunter.screen.profile;

import android.app.Application;

import androidx.annotation.Nullable;

import com.example.hunter.data.local.db.HunterDatabase;
import com.example.hunter.data.local.preference.PreferenceRepository;
import com.example.hunter.data.remote.RemoteRepository;
import com.example.hunter.screen.history.HistoryContract;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ProfilePresenter implements ProfileContract.Presenter {

    private static final String TAG = ProfilePresenter.class.getSimpleName();

    private PreferenceRepository preferenceRepo;
    private Application application;
    private RemoteRepository remoteRepository;
    private HunterDatabase oaseDatabase;


    private CompositeDisposable compositeDisposable;

    @Nullable
    private ProfileContract.View mView;

    @Inject
    ProfilePresenter(Application application, RemoteRepository remoteRepository,
                     PreferenceRepository preferenceRepo, HunterDatabase oaseDatabase) {
        this.preferenceRepo = preferenceRepo;
        this.application = application;
        this.remoteRepository = remoteRepository;
        this.oaseDatabase = oaseDatabase;

        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void takeView(ProfileContract.View view) {
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
                    String logoProfile = oaseDatabase.userDao().getPicture();
                    String name = oaseDatabase.userDao().getName();
                    String hp = oaseDatabase.userDao().getNoHp();
                    mView.setHomeInformation(logoProfile,name,hp);

                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe()

        );
    }

    @Override
    public void logout() {
        if(mView==null){
            return;
        }

        compositeDisposable.add(
                Completable.fromAction(() -> {
                    oaseDatabase.userDao().deleteUser();
                    preferenceRepo.setUserLogged(false);
                    mView.goToLogin();
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe()

        );
    }
}