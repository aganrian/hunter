package com.example.hunter.screen.otp;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.hunter.screen.login.LoginFragment;
import com.example.hunter.utils.ActivityUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;
import com.example.hunter.R;

/*aktifitas otp yang di panggil saat register berhasil atau lupa password*/
public class OtpActivity extends DaggerAppCompatActivity {

    public static String USER_ID = "USER_ID";
    public static String USER_EMAIL = "USER_EMAIL";
    public static String USER_NAMA = "USER_NAMA";
    public static String FROM = "FROM";

    @Inject
    OtpFragment mFragment;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        mUnbinder = ButterKnife.bind(this);

        OtpFragment otpFragment = (OtpFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if (otpFragment == null) {
            otpFragment = mFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    otpFragment, R.id.fragment_container);
        }
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }


}
