package com.example.hunter.data;

import android.app.Application;

import androidx.room.Room;


import com.example.hunter.data.local.db.HunterDatabase;
import com.example.hunter.data.local.preference.PreferenceDataSource;
import com.example.hunter.data.local.preference.PreferenceRepository;
import com.example.hunter.data.remote.RemoteDataSource;
import com.example.hunter.data.remote.RemoteRepository;
import com.example.hunter.data.remote.RemoteService;
import com.example.hunter.data.remote.interceptor.ResponseInterceptor;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class RepositoryModule {

    @Binds
    abstract RemoteRepository remoteRepository(RemoteDataSource dataSource);

    @Binds
    abstract PreferenceRepository preferenceRepository(PreferenceDataSource dataSource);

    @Provides
    @Singleton
    static ResponseInterceptor refreshTokenInterceptor(Application application,
                                                       PreferenceRepository preferenceRepository) {
        return new ResponseInterceptor(application, preferenceRepository);
    }

    @Provides
    @Singleton
    static RemoteService remoteService(ResponseInterceptor interceptor) {
        return new RemoteService(interceptor);
    }

    @Provides
    @Singleton
    static HunterDatabase oaseDatabase(Application application) {
        return Room.databaseBuilder(application, HunterDatabase.class, "oase-app.db").build();
    }
}