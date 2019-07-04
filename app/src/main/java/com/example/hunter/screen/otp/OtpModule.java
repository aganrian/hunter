package com.example.hunter.screen.otp;


import android.os.Bundle;

import com.example.hunter.di.ActivityScoped;
import com.example.hunter.di.FragmentScoped;
import com.example.hunter.screen.login.LoginActivity;
import com.example.hunter.screen.login.LoginContract;
import com.example.hunter.screen.login.LoginFragment;
import com.example.hunter.screen.login.LoginPresenter;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class OtpModule {

    @ActivityScoped
    @Binds
    abstract OtpContract.Presenter otpPresenter(OtpPresenter presenter);

    @FragmentScoped
    @ContributesAndroidInjector
    abstract OtpFragment otpFragment();

    @Provides
    @ActivityScoped
    static Bundle provideExtras(OtpActivity activity) {
        return activity.getIntent().getExtras();
    }

    @Provides
    @Named("USER_ID")
    @ActivityScoped
    static Integer provideUserId(OtpActivity activity) {
        return activity.getIntent().getExtras().getInt(OtpActivity.USER_ID);
    }

    @Provides
    @Named("USER_EMAIL")
    @ActivityScoped
    static String provideUserEmail(OtpActivity activity) {
        return activity.getIntent().getExtras().getString(OtpActivity.USER_EMAIL);
    }

    @Provides
    @Named("FROM")
    @ActivityScoped
    static String provideFrom(OtpActivity activity) {
        return activity.getIntent().getExtras().getString(OtpActivity.FROM);
    }

    @Provides
    @Named("USER_NAMA")
    @ActivityScoped
    static String proviceUserNama(OtpActivity activity) {
        return activity.getIntent().getExtras().getString(OtpActivity.USER_NAMA);
    }

}