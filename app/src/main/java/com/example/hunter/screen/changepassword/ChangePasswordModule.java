package com.example.hunter.screen.changepassword;


import android.os.Bundle;

import com.example.hunter.di.ActivityScoped;
import com.example.hunter.di.FragmentScoped;
import com.example.hunter.screen.biodata.BiodataActivity;
import com.example.hunter.screen.forgotpassword.ForgotPasswordActivity;
import com.example.hunter.screen.forgotpassword.ForgotPasswordContract;
import com.example.hunter.screen.forgotpassword.ForgotPasswordFragment;
import com.example.hunter.screen.forgotpassword.ForgotPasswordPresenter;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ChangePasswordModule {

    @ActivityScoped
    @Binds
    abstract ChangePasswordContract.Presenter changePasswordPresenter(ChangePasswordPresenter presenter);

    @FragmentScoped
    @ContributesAndroidInjector
    abstract ChangePasswordFragment changePasswordFragment();

    @Provides
    @ActivityScoped
    static Bundle provideExtras(ChangePasswordActivity activity) {
        return activity.getIntent().getExtras();
    }

    @Provides
    @Named("USER_ID_CHANGE")
    @ActivityScoped
    static Integer provideUserId(ChangePasswordActivity activity) {
        return activity.getIntent().getExtras().getInt(ChangePasswordActivity.USER_ID);
    }

    @Provides
    @Named("USER_EMAIL_CHANGE")
    @ActivityScoped
    static String provideUserEmail(ChangePasswordActivity activity) {
        return activity.getIntent().getExtras().getString(ChangePasswordActivity.USER_EMAIL);
    }

}