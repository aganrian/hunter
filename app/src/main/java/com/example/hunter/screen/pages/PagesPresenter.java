package com.example.hunter.screen.pages;

import android.app.Application;

import androidx.annotation.Nullable;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.example.hunter.data.local.db.HunterDatabase;
import com.example.hunter.data.local.db.entity.UserEntity;
import com.example.hunter.data.local.preference.PreferenceRepository;
import com.example.hunter.data.remote.RemoteRepository;
import com.example.hunter.screen.login.LoginContract;
import com.example.hunter.utils.StringUtils;
import com.google.gson.Gson;

import org.json.JSONObject;

import javax.inject.Inject;

import com.example.hunter.R;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class PagesPresenter implements PagesContract.Presenter {

    private static final String TAG = PagesPresenter.class.getSimpleName();

    private PreferenceRepository preferenceRepo;
    private Application application;
    private RemoteRepository remoteRepository;
    private HunterDatabase oaseDatabase;


    private CompositeDisposable compositeDisposable;

    @Nullable
    private PagesContract.View mView;

    @Inject
    PagesPresenter(Application application, RemoteRepository remoteRepository,
                   PreferenceRepository preferenceRepo, HunterDatabase oaseDatabase) {
        this.preferenceRepo = preferenceRepo;
        this.application = application;
        this.remoteRepository = remoteRepository;
        this.oaseDatabase = oaseDatabase;

        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void takeView(PagesContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
        compositeDisposable.clear();
    }

    /*memanggil data pages yang di tentukan*/
    @Override
    public void getContent(Integer id) {
        if(mView==null){
            return;
        }

        mView.setLoadingIndicator(true);
        compositeDisposable.add(remoteRepository.pagesBeans(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(reponse -> {
                    mView.setLoadingIndicator(false);
                   mView.setContent(reponse.getData().getContent());
                }, error -> {
                    mView.setLoadingIndicator(false);
                    ANError anError = (ANError) error;
                    String json = new Gson().toJson(anError);

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