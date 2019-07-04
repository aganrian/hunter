package com.example.hunter.screen.profile;

import com.example.hunter.di.ActivityScoped;
import com.example.hunter.di.FragmentScoped;
import com.example.hunter.screen.history.HistoryContract;
import com.example.hunter.screen.history.HistoryFragment;
import com.example.hunter.screen.history.HistoryPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ProfileModule {

    @ActivityScoped
    @Binds
    abstract ProfileContract.Presenter profilePresenter(ProfilePresenter presenter);

    @FragmentScoped
    @ContributesAndroidInjector
    abstract ProfileFragment profileFragment();

}