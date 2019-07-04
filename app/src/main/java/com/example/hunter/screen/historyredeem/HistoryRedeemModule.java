package com.example.hunter.screen.historyredeem;

import com.example.hunter.adapter.HistoryReedemAdapter;
import com.example.hunter.adapter.HistoryReportAdapter;
import com.example.hunter.di.ActivityScoped;
import com.example.hunter.di.FragmentScoped;
import com.example.hunter.screen.historyreport.HistoryReportContract;
import com.example.hunter.screen.historyreport.HistoryReportFragment;
import com.example.hunter.screen.historyreport.HistoryReportPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class HistoryRedeemModule {

    @ActivityScoped
    @Binds
    abstract HistoryRedeemContract.Presenter historyRedeemPresenter(HistoryRedeemPresenter presenter);

    @FragmentScoped
    @ContributesAndroidInjector
    abstract HistoryRedeemFragment historyRedeemFragment();

    @Provides
    @ActivityScoped
    static HistoryReedemAdapter historyReportAdapter() {
        return new HistoryReedemAdapter();
    }

}