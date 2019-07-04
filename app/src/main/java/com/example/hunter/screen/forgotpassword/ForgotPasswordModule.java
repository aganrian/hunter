package com.example.hunter.screen.forgotpassword;


import android.os.Bundle;

import com.example.hunter.di.ActivityScoped;
import com.example.hunter.di.FragmentScoped;
import com.example.hunter.screen.otp.OtpActivity;
import com.example.hunter.screen.otp.OtpContract;
import com.example.hunter.screen.otp.OtpFragment;
import com.example.hunter.screen.otp.OtpPresenter;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ForgotPasswordModule {

    @ActivityScoped
    @Binds
    abstract ForgotPasswordContract.Presenter forgotPasswordPresenter(ForgotPasswordPresenter presenter);

    @FragmentScoped
    @ContributesAndroidInjector
    abstract ForgotPasswordFragment otpFragment();

    @Provides
    @ActivityScoped
    static Bundle provideExtras(ForgotPasswordActivity activity) {
        return activity.getIntent().getExtras();
    }

}