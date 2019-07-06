package com.example.hunter.screen.forgotpassword;

import android.app.Application;
import android.util.Log;

import androidx.annotation.Nullable;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.example.hunter.data.local.db.HunterDatabase;
import com.example.hunter.data.local.db.entity.UserEntity;
import com.example.hunter.data.local.preference.PreferenceRepository;
import com.example.hunter.data.remote.RemoteRepository;
import com.example.hunter.screen.otp.OtpContract;
import com.example.hunter.utils.StringUtils;

import org.json.JSONObject;

import javax.inject.Inject;

import id.oase.indonesia.oasebrdiepa.R;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ForgotPasswordPresenter implements ForgotPasswordContract.Presenter {

    private static final String TAG = ForgotPasswordPresenter.class.getSimpleName();

    private PreferenceRepository preferenceRepo;
    private Application application;
    private RemoteRepository remoteRepository;
    private HunterDatabase oaseDatabase;


    private CompositeDisposable compositeDisposable;

    @Nullable
    private ForgotPasswordContract.View mView;

    @Inject
    ForgotPasswordPresenter(Application application, RemoteRepository remoteRepository,
                            PreferenceRepository preferenceRepo, HunterDatabase oaseDatabase) {
        this.preferenceRepo = preferenceRepo;
        this.application = application;
        this.remoteRepository = remoteRepository;
        this.oaseDatabase = oaseDatabase;

        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void takeView(ForgotPasswordContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
        compositeDisposable.clear();
    }

    @Override
    public void callForgotPassword(String email) {

        if(mView==null){
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


        mView.setLoadingIndicator(true);
        compositeDisposable.add(remoteRepository.postResetPassword(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(reponse -> {
                    mView.setLoadingIndicator(false);

                    mView.setForgotPassword(reponse.getId_user(),email);


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