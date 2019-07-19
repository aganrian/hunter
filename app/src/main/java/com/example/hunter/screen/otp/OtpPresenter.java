package com.example.hunter.screen.otp;

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
import com.example.hunter.screen.register.RegisterActivity;
import com.example.hunter.utils.StringUtils;

import org.json.JSONObject;

import javax.inject.Inject;

import com.example.hunter.R;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class OtpPresenter implements OtpContract.Presenter {

    private static final String TAG = OtpPresenter.class.getSimpleName();

    private PreferenceRepository preferenceRepo;
    private Application application;
    private RemoteRepository remoteRepository;
    private HunterDatabase oaseDatabase;


    private CompositeDisposable compositeDisposable;

    @Nullable
    private OtpContract.View mView;

    @Inject
    OtpPresenter(Application application, RemoteRepository remoteRepository,
                 PreferenceRepository preferenceRepo, HunterDatabase oaseDatabase) {
        this.preferenceRepo = preferenceRepo;
        this.application = application;
        this.remoteRepository = remoteRepository;
        this.oaseDatabase = oaseDatabase;

        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void takeView(OtpContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
        compositeDisposable.clear();
    }


    @Override
    public void resendCodeOtp(Integer userId) {
        if(mView==null){
            return;
        }

        mView.setLoadingIndicator(true);
        compositeDisposable.add(remoteRepository.resendOtp(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(reponse -> {
                    mView.setLoadingIndicator(false);
                    mView.showErrorMessage(reponse.getStatus());
                }, error -> {
                    mView.setLoadingIndicator(false);

                    ANError anError = (ANError) error;
                    if (anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)) {
                        mView.showErrorMessage(application.getString(R.string.message_connection_lost));
                    } else {
                        if (anError.getErrorBody() != null) {
                            JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                            mView.showErrorMessage(jsonObject.optString("status"));
                        }
                    }
                })
        );

    }


    /*Validasi Otp ke backend*/
    @Override
    public void sendOtp(Integer userId, String otp,String from) {
        if(mView==null){
            return;
        }

        mView.setLoadingIndicator(true);
        if(from.equalsIgnoreCase(RegisterActivity.class.getName())){
            compositeDisposable.add(remoteRepository.sendOtp(userId,otp)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(reponse -> {
                        mView.setLoadingIndicator(false);
                        mView.goToIsiProfile();
                    }, error -> {
                        mView.setLoadingIndicator(false);

                        ANError anError = (ANError) error;
                        if (anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)) {
                            mView.showErrorMessage(application.getString(R.string.message_connection_lost));
                        } else {
                            if (anError.getErrorBody() != null) {
                                JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                                mView.showErrorMessage(jsonObject.optString("status"));
                            }
                        }
                    })
            );
        }else{
            compositeDisposable.add(remoteRepository.sendOtpForgot(userId,otp)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(reponse -> {
                        mView.setLoadingIndicator(false);
                        mView.goToChangePassword();
                    }, error -> {
                        mView.setLoadingIndicator(false);

                        ANError anError = (ANError) error;
                        if (anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)) {
                            mView.showErrorMessage(application.getString(R.string.message_connection_lost));
                        } else {
                            if (anError.getErrorBody() != null) {
                                JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                                mView.showErrorMessage(jsonObject.optString("status"));
                            }
                        }
                    })
            );
        }

    }
}