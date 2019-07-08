package com.example.hunter.screen.pages;


import android.os.Bundle;

import com.example.hunter.di.ActivityScoped;
import com.example.hunter.di.FragmentScoped;
import com.example.hunter.screen.fotoplatreport.FotoPlatReportActivity;
import com.example.hunter.screen.login.LoginActivity;
import com.example.hunter.screen.login.LoginContract;
import com.example.hunter.screen.login.LoginFragment;
import com.example.hunter.screen.login.LoginPresenter;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class PagesModule {

    @ActivityScoped
    @Binds
    abstract PagesContract.Presenter pagesPresenter(PagesPresenter presenter);

    @FragmentScoped
    @ContributesAndroidInjector
    abstract PagesFragment pagesFragment();

    @Provides
    @ActivityScoped
    static Bundle provideExtras(PagesActivity activity) {
        return activity.getIntent().getExtras();
    }

    @Provides
    @Named("ID_PAGES")
    @ActivityScoped
    static Integer provideIdPages(PagesActivity activity) {
        return activity.getIntent().getExtras().getInt(PagesActivity.ID_PAGES);
    }

    @Provides
    @Named("TITLE_PAGES")
    @ActivityScoped
    static String proviceTitle(PagesActivity activity) {
        return activity.getIntent().getExtras().getString(PagesActivity.TITLE_PAGES);
    }

}