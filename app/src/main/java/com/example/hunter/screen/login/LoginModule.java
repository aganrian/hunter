package com.example.hunter.screen.login;


import android.os.Bundle;

import com.example.hunter.di.ActivityScoped;
import com.example.hunter.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class LoginModule {

    @ActivityScoped
    @Binds
    abstract LoginContract.Presenter loginPresenter(LoginPresenter presenter);

    @FragmentScoped
    @ContributesAndroidInjector
    abstract LoginFragment loginFragment();

    @Provides
    @ActivityScoped
    static Bundle provideExtras(LoginActivity activity) {
        return activity.getIntent().getExtras();
    }

}