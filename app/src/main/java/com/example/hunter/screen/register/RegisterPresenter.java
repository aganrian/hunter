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

import org.json.JSONObject;

import javax.inject.Inject;

import com.example.hunter.R;
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

    /*metode yang di daftarkan di register contract dan akan melakukan pemanggilan ke register API */
    @Override
    public void doRegister(String nama, String email, String password) {

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
                    /*ketika berhasil akan memanggil register api maka akan memanggil view untuk ke otp*/
                        mView.goToOtpActivity(reponse.getData().getId_user(),reponse.getData().getNama_lengkap()
                                ,reponse.getData().getEmail());
                }, error -> {
                    mView.setLoadingIndicator(false);

                    ANError anError = (ANError) error;
                    if (anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)) {
                        mView.showErrorMessage(application.getString(R.string.message_connection_lost));
                    } else {
                        if (anError.getErrorBody() != null) {
                            if (anError.getErrorBody() != null) {
                                JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                                mView.showErrorMessage(jsonObject.optString("status"));
                            }
                        }
                    }
                })
        );

    }
}