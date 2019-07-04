package com.example.hunter.screen.register;


import android.os.Bundle;

import com.example.hunter.di.ActivityScoped;
import com.example.hunter.di.FragmentScoped;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class RegisterModule {

    @ActivityScoped
    @Binds
    abstract RegisterContract.Presenter registerPresenter(RegisterPresenter presenter);

    @FragmentScoped
    @ContributesAndroidInjector
    abstract RegisterFragment registerFragment();

    @Provides
    @ActivityScoped
    static Bundle provideExtras(RegisterActivity activity) {
        return activity.getIntent().getExtras();
    }

}