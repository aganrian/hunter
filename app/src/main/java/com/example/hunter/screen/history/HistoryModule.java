package com.example.hunter.screen.history;

import com.example.hunter.di.ActivityScoped;
import com.example.hunter.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class HistoryModule {

    @ActivityScoped
    @Binds
    abstract HistoryContract.Presenter historyPresenter(HistoryPresenter presenter);

    @FragmentScoped
    @ContributesAndroidInjector
    abstract HistoryFragment historyFragment();

}