package com.example.hunter.screen.forgotpassword;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.hunter.screen.otp.OtpFragment;
import com.example.hunter.utils.ActivityUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;
import id.oase.indonesia.oasebrdiepa.R;

public class ForgotPasswordActivity extends DaggerAppCompatActivity {



    @Inject
    ForgotPasswordFragment mFragment;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        mUnbinder = ButterKnife.bind(this);

        ForgotPasswordFragment forgotPasswordFragment = (ForgotPasswordFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if (forgotPasswordFragment == null) {
            forgotPasswordFragment = mFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    forgotPasswordFragment, R.id.fragment_container);
        }
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }


}
