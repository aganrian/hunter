package com.example.hunter.screen.announcement;

import com.example.hunter.adapter.AnnouncementAdapter;
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
public abstract class AnnouncementModule {

    @ActivityScoped
    @Binds
    abstract AnnouncementContract.Presenter announcementPresenter(AnnouncementPresenter presenter);

    @FragmentScoped
    @ContributesAndroidInjector
    abstract AnnouncementFragment announcementFragment();

    @Provides
    @ActivityScoped
    static AnnouncementAdapter historyReportAdapter() {
        return new AnnouncementAdapter();
    }

}