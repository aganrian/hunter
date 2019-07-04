package com.example.hunter.data.remote.interceptor;

import android.app.Application;
import android.util.Log;


import com.example.hunter.data.local.preference.PreferenceRepository;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by febri on 11/12/16. Used to intercept response header and act accordingly
 */

public class ResponseInterceptor implements Interceptor
{
    private Application application;
    private PreferenceRepository preferenceRepo;

    @Inject
    public ResponseInterceptor(Application application, PreferenceRepository preferenceRepo) {
        this.application = application;
        this.preferenceRepo = preferenceRepo;
    }

    @Override
    public Response intercept (Chain chain) throws IOException
    {
        Response lResponse = chain.proceed(chain.request());
        // TODO: implement your intercept logic below

        if (lResponse.code() == 401)
        {
            // unauthorized
            Log.d("interceptor", "401");
        }
        else if (lResponse.code() == 403)
        {
            // forbidden
            Log.d("interceptor", "403");
        }
        else if (lResponse.code() == 404)
        {
            // endpoint not found
            Log.d("interceptor", "404");
        }
        else if (lResponse.code() == 500)
        {
            // internal server error
            Log.d("interceptor", "500");
        }
        else if (lResponse.code() == 502)
        {
            // bad gateway
            Log.d("interceptor", "502");
        }
        else if (lResponse.code() == 503)
        {
            // service unavailable
            Log.d("interceptor", "503");
        }
        else if (lResponse.code() == 504)
        {
            // gateway timeout
            Log.d("interceptor", "504");
        }

        return lResponse;
    }


}
