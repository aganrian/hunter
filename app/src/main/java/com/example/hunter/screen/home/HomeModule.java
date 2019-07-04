package com.example.hunter.screen.home;



import android.content.Context;

import com.example.hunter.adapter.ProductAdapter;
import com.example.hunter.di.ActivityScoped;
import com.example.hunter.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class HomeModule {

    @ActivityScoped
    @Binds
    abstract HomeContract.Presenter homePresenter(HomePresenter presenter);

    @FragmentScoped
    @ContributesAndroidInjector
    abstract HomeFragment homeFragment();

    @Provides
    @ActivityScoped
    static ProductAdapter beritaAdapter(Context context) {
        return new ProductAdapter(context);
    }

}