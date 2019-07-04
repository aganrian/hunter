package com.example.hunter.screen.historyreport;

import android.content.Context;

import com.example.hunter.adapter.HistoryReportAdapter;
import com.example.hunter.adapter.ProductAdapter;
import com.example.hunter.di.ActivityScoped;
import com.example.hunter.di.FragmentScoped;
import com.example.hunter.screen.history.HistoryContract;
import com.example.hunter.screen.history.HistoryFragment;
import com.example.hunter.screen.history.HistoryPresenter;

import javax.inject.Inject;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class HistoryReportModule {

    @ActivityScoped
    @Binds
    abstract HistoryReportContract.Presenter historyReportPresenter(HistoryReportPresenter presenter);

    @FragmentScoped
    @ContributesAndroidInjector
    abstract HistoryReportFragment historReportFragment();

    @Provides
    @ActivityScoped
    static HistoryReportAdapter historyReportAdapter() {
        return new HistoryReportAdapter();
    }

}