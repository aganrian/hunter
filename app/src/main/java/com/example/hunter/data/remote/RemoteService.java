package com.example.hunter.data.remote;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.gsonparserfactory.GsonParserFactory;
import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.example.hunter.BuildConfig;
import com.example.hunter.data.remote.interceptor.ResponseInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Inject;

import okhttp3.OkHttpClient;



/*Class fungsi untuk remote API ke server*/
public class RemoteService {

    private ResponseInterceptor refreshTokenInterceptor;

    @Inject
    public RemoteService(ResponseInterceptor refreshTokenInterceptor) {
        this.refreshTokenInterceptor = refreshTokenInterceptor;
    }

    public void init(Application application) {

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .addInterceptor(refreshTokenInterceptor)
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        AndroidNetworking.initialize(application, okHttpClient);
        AndroidNetworking.setParserFactory(new GsonParserFactory(gson));
        if (BuildConfig.DEBUG) {
            AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.HEADERS);
        }
    }
}