package com.example.hunter.screen.register;

import android.app.Application;

import androidx.annotation.Nullable;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.example.hunter.data.local.db.HunterDatabase;
import com.example.hunter.data.local.db.entity.UserEntity;
import com.example.hunter.data.local.preference.PreferenceRepository;
import com.example.hunter.data.remote.RemoteRepository;
import com.example.hunter.utils.StringUtils;

import javax.inject.Inject;

import id.oase.indonesia.oasebrdiepa.R;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class RegisterPresenter implements RegisterContract.Presenter {

    private static final String TAG = RegisterPresenter.class.getSimpleName();

    private PreferenceRepository preferenceRepo;
    private Application application;
    private RemoteRepository remoteRepository;
    private HunterDatabase oaseDatabase;


    private CompositeDisposable compositeDisposable;

    @Nullable
    private RegisterContract.View mView;

    @Inject
    RegisterPresenter(Application application, RemoteRepository remoteRepository,
                      PreferenceRepository preferenceRepo, HunterDatabase oaseDatabase) {
        this.preferenceRepo = preferenceRepo;
        this.application = application;
        this.remoteRepository = remoteRepository;
        this.oaseDatabase = oaseDatabase;

        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void takeView(RegisterContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
        compositeDisposable.clear();
    }

    @Override
    public void doLogin(String nama,String email, String password) {

        if (mView == null) {
            return;
        }

        if(email.equalsIgnoreCase("")){
            mView.showErrorMessage(application.getString(R.string.err_message_nohandphone));
            return;
        }

        if(!StringUtils.isValidEmailId(email)){
            mView.showErrorMessage(application.getString(R.string.err_message_emailnotvalid));
            return;
        }

        if(password.equalsIgnoreCase("")){
            mView.showErrorMessage(application.getString(R.string.err_message_password));
            return;
        }

        mView.setLoadingIndicator(true);
        compositeDisposable.add(remoteRepository.postUserRegister(email,password,nama)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(reponse -> {
                    mView.setLoadingIndicator(false);
                        mView.goToOtpActivity(reponse.getData().getId_user(),reponse.getData().getNama_lengkap()
                                ,reponse.getData().getEmail());
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
}