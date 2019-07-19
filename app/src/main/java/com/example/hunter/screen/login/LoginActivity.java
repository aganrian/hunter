package com.example.hunter.screen.login;

import android.os.Bundle;

import androidx.annotation.Nullable;


import com.example.hunter.utils.ActivityUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;
import com.example.hunter.R;

/*aktifitas yang di lakukan pada login*/
public class LoginActivity extends DaggerAppCompatActivity {


    @Inject
    LoginFragment mFragment;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUnbinder = ButterKnife.bind(this);

        LoginFragment verificationFragment = (LoginFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if (verificationFragment == null) {
            verificationFragment = mFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    verificationFragment, R.id.fragment_container);
        }
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }


}
