package com.example.hunter.screen.biodata;


import android.os.Bundle;

import com.example.hunter.di.ActivityScoped;
import com.example.hunter.di.FragmentScoped;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BiodataModule {

    @ActivityScoped
    @Binds
    abstract BiodataContract.Presenter biodataPresenter(BiodataPresenter presenter);

    @FragmentScoped
    @ContributesAndroidInjector
    abstract BiodataFragment biodataFragment();

    @Provides
    @ActivityScoped
    static Bundle provideExtras(BiodataActivity activity) {
        return activity.getIntent().getExtras();
    }

    @Provides
    @Named("USER_ID_BIODATA")
    @ActivityScoped
    static Integer provideUserId(BiodataActivity activity) {
        return activity.getIntent().getExtras().getInt(BiodataActivity.USER_ID);
    }

    @Provides
    @Named("USER_EMAIL_BIODATA")
    @ActivityScoped
    static String provideUserEmail(BiodataActivity activity) {
        return activity.getIntent().getExtras().getString(BiodataActivity.USER_EMAIL);
    }

    @Provides
    @Named("USER_OTP_HIDDEN")
    @ActivityScoped
    static String provideUserOtp(BiodataActivity activity) {
        return activity.getIntent().getExtras().getString(BiodataActivity.USER_OTP);
    }

    @Provides
    @Named("USER_NAMA_BIODATA")
    @ActivityScoped
    static String provideUserEmailNama(BiodataActivity activity) {
        return activity.getIntent().getExtras().getString(BiodataActivity.USER_NAMA);
    }

}