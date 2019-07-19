package com.example.hunter.screen.register;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.hunter.screen.login.LoginFragment;
import com.example.hunter.utils.ActivityUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;
import com.example.hunter.R;

/*activitas register */
public class RegisterActivity extends DaggerAppCompatActivity {

    @Inject
    RegisterFragment mFragment;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mUnbinder = ButterKnife.bind(this);

        RegisterFragment registerFragment = (RegisterFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if (registerFragment == null) {
            registerFragment = mFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    registerFragment, R.id.fragment_container);
        }
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }

}
