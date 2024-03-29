package com.example.hunter.screen.changepassword;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.hunter.screen.forgotpassword.ForgotPasswordFragment;
import com.example.hunter.utils.ActivityUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;
import com.example.hunter.R;

/*class ketika otp saat ganti password berhasil di arahkan ganti password*/
public class ChangePasswordActivity extends DaggerAppCompatActivity {

    public static String USER_ID = "USER_ID";
    public static String USER_EMAIL = "USER_EMAIL";

    @Inject
    ChangePasswordFragment mFragment;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        mUnbinder = ButterKnife.bind(this);

        ChangePasswordFragment changePasswordFragment = (ChangePasswordFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if (changePasswordFragment == null) {
            changePasswordFragment = mFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    changePasswordFragment, R.id.fragment_container);
        }
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }


}
