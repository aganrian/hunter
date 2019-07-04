package com.example.hunter.screen.splash;

import com.example.hunter.di.ActivityScoped;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class SplashModule {

    @ActivityScoped
    @Binds
    abstract SplashContract.Presenter splashPresenter(SplashPresenter presenter);

}