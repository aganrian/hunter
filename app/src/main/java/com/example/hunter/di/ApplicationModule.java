package com.example.hunter.di;

import android.app.Application;
import android.content.Context;

import com.example.hunter.service.MyFirebaseMessagingService;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
abstract class ApplicationModule {

    @Binds
    abstract Context bindContext(Application application);

    @Provides
    static MyFirebaseMessagingService firebaseMessagingService() {
        return new MyFirebaseMessagingService();
    }
}