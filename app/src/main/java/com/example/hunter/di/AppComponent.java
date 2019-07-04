package com.example.hunter.di;

import android.app.Application;


import com.example.hunter.HunterApplication;
import com.example.hunter.data.RepositoryModule;
import com.example.hunter.service.MyFirebaseMessagingService;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        ActivityBindingModule.class,
        RepositoryModule.class,
        AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<HunterApplication> {

    void inject(MyFirebaseMessagingService service);

    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}