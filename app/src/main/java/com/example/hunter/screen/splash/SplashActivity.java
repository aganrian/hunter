package com.example.hunter.screen.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;


import com.crashlytics.android.Crashlytics;
import com.example.hunter.screen.login.LoginActivity;
import com.example.hunter.screen.main.MainActivity;

import io.fabric.sdk.android.Fabric;
import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;
import id.oase.indonesia.oasebrdiepa.R;


public class SplashActivity extends DaggerAppCompatActivity implements SplashContract.View {

    private Unbinder mUnbinder;
    public static final int SPLASH_TIME = 2000;


    @Inject
    SplashContract.Presenter mPresenterSplash;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash);
        mUnbinder = ButterKnife.bind(this);
        mPresenterSplash.takeView(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenterSplash.checkUserExisting();
            }
        },SPLASH_TIME);



    }

    @Override
    public void gotoHome() {
        Intent i = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void gotoLogin() {
        Intent i = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onDestroy() {
        mPresenterSplash.dropView();
        super.onDestroy();
    }
}
