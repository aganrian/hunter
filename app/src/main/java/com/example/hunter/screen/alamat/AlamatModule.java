package com.example.hunter.screen.alamat;


import android.os.Bundle;

import com.example.hunter.adapter.HistoryReedemAdapter;
import com.example.hunter.adapter.ListCustomAdapter;
import com.example.hunter.di.ActivityScoped;
import com.example.hunter.di.FragmentScoped;
import com.example.hunter.screen.login.LoginActivity;
import com.example.hunter.screen.login.LoginContract;
import com.example.hunter.screen.login.LoginFragment;
import com.example.hunter.screen.login.LoginPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AlamatModule {

    @ActivityScoped
    @Binds
    abstract AlamatContract.Presenter alamatPresenter(AlamatPresenter presenter);

    @FragmentScoped
    @ContributesAndroidInjector
    abstract AlamatFragment alamatFragment();

    @Provides
    @ActivityScoped
    static Bundle provideExtras(AlamatActivity activity) {
        return activity.getIntent().getExtras();
    }


}