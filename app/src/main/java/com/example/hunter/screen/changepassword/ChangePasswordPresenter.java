package com.example.hunter.screen.changepassword;

import android.app.Application;

import androidx.annotation.Nullable;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.example.hunter.data.local.db.HunterDatabase;
import com.example.hunter.data.local.preference.PreferenceRepository;
import com.example.hunter.data.remote.RemoteRepository;
import com.example.hunter.screen.forgotpassword.ForgotPasswordContract;
import com.example.hunter.utils.StringUtils;

import org.json.JSONObject;

import javax.inject.Inject;

import id.oase.indonesia.oasebrdiepa.R;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ChangePasswordPresenter implements ChangePasswordContract.Presenter {

    private static final String TAG = ChangePasswordPresenter.class.getSimpleName();

    private PreferenceRepository preferenceRepo;
    private Application application;
    private RemoteRepository remoteRepository;
    private HunterDatabase oaseDatabase;


    private CompositeDisposable compositeDisposable;

    @Nullable
    private ChangePasswordContract.View mView;

    @Inject
    ChangePasswordPresenter(Application application, RemoteRepository remoteRepository,
                            PreferenceRepository preferenceRepo, HunterDatabase oaseDatabase) {
        this.preferenceRepo = preferenceRepo;
        this.application = application;
        this.remoteRepository = remoteRepository;
        this.oaseDatabase = oaseDatabase;

        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void takeView(ChangePasswordContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
        compositeDisposable.clear();
    }

    @Override
    public void changePassword(Integer userId,String pass, String confirmPass) {
        if(mView==null){
            return;
        }

        if(pass.equalsIgnoreCase("")){
            mView.showErrorMessage(application.getString(R.string.fillPassword));
            return;
        }

        if(confirmPass.equalsIgnoreCase("")){
            mView.showErrorMessage(application.getString(R.string.ConfirmPassword));
            return;
        }

        if(!pass.equalsIgnoreCase(confirmPass)){
            mView.showErrorMessage(application.getString(R.string.ConfirmPasswordSame));
            return;
        }

        compositeDisposable.add(remoteRepository.changePassword(String.valueOf(userId),pass)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(reponse -> {
                    mView.setLoadingIndicator(false);
                    mView.goToLogin();
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