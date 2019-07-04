package com.example.hunter.screen.biodata;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.hunter.screen.login.LoginFragment;
import com.example.hunter.utils.ActivityUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;
import id.oase.indonesia.oasebrdiepa.R;

public class BiodataActivity extends DaggerAppCompatActivity {

    public static String USER_ID = "USER_ID";
    public static String USER_EMAIL = "USER_EMAIL";
    public static String USER_OTP = "USER_OTP";
    public static String USER_NAMA = "USER_NAMA";
    @Inject
    BiodataFragment mFragment;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biodata);
        mUnbinder = ButterKnife.bind(this);

        BiodataFragment biodataFragment = (BiodataFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if (biodataFragment == null) {
            biodataFragment = mFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    biodataFragment, R.id.fragment_container);
        }
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }


}
