package com.example.hunter.screen.splash;

import android.util.Log;

import androidx.annotation.Nullable;


import com.example.hunter.data.local.db.HunterDatabase;
import com.example.hunter.data.local.preference.PreferenceRepository;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;


public class SplashPresenter implements SplashContract.Presenter {

    private static final String TAG = SplashPresenter.class.getSimpleName();

    private PreferenceRepository preferenceRepo;
    private HunterDatabase oaseDatabase;
    private CompositeDisposable compositeDisposable ;

    @Nullable
    private SplashContract.View mView;

    @Inject
    SplashPresenter(HunterDatabase oaseDatabase,PreferenceRepository preferenceRepo) {
        this.preferenceRepo = preferenceRepo;
        this.oaseDatabase = oaseDatabase;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void takeView(SplashContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }


    /*presenter check sudah pernah login atau belum*/
    @Override
    public void checkUserExisting(){

        if(mView==null){
            return;
        }

        Log.e("String log user login",String.valueOf(preferenceRepo.isUserLogged()));

        if (preferenceRepo.isUserLogged()) {
            mView.gotoHome();
        } else {
            mView.gotoLogin();
        }

    }

}