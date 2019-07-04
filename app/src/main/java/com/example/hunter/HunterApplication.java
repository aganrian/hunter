package com.example.hunter;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.example.hunter.data.remote.RemoteService;
import com.example.hunter.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import id.oase.indonesia.oasebrdiepa.BuildConfig;
import io.fabric.sdk.android.Fabric;

public class HunterApplication extends DaggerApplication {

    @Inject
    RemoteService remoteService;

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        remoteService.init(this);

//        EmojiManager.install(new GoogleEmojiProvider());
        Crashlytics crashlytics = new Crashlytics.Builder()
                .core(new CrashlyticsCore.Builder()
                        .disabled(BuildConfig.DEBUG)
                        .build())
                .build();

        final Fabric fabric = new Fabric.Builder(this)
                .kits(crashlytics)
                .debuggable(true)
                .build();

        Fabric.with(fabric);

    }
}
