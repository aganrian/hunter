package com.example.hunter.data.local.preference;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

import id.oase.indonesia.oasebrdiepa.BuildConfig;


public class PreferenceDataSource implements PreferenceRepository {

    private SharedPreferences mPreferences;

    //initialisasi key pref shared
    private static final String PREF_USER_PHONE_NUMBER = "PREF_USER_PHONE_NUMBER";
    private static final String PREF_IS_LOGGED = "IS_LOGGED";

    @Inject
    PreferenceDataSource(Application application) {
        mPreferences = application.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
    }

    @Override
    public boolean isUserLogged() {
        return mPreferences.getBoolean(PREF_IS_LOGGED, false);
    }

    @Override
    public void setUserLogged(boolean userLogged) {
        mPreferences.edit().putBoolean(PREF_IS_LOGGED, userLogged).apply();
    }
}
