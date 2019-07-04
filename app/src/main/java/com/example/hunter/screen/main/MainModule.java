package com.example.hunter.screen.main;
import androidx.fragment.app.FragmentManager;

import com.example.hunter.di.ActivityScoped;
import com.example.hunter.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract MainFragment mainFragment();

    @ActivityScoped
    @Binds
    abstract MainContract.Presenter mainPresenter(MainPresenter presenter);

    @Provides
    @ActivityScoped
    static FragmentManager provideFragmentManager(MainActivity activity) {
        return activity.getSupportFragmentManager();
    }
}
