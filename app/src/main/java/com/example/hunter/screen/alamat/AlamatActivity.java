package com.example.hunter.screen.alamat;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.hunter.screen.login.LoginFragment;
import com.example.hunter.utils.ActivityUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;
import id.oase.indonesia.oasebrdiepa.R;

public class AlamatActivity extends DaggerAppCompatActivity {


    @Inject
    AlamatFragment mFragment;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alamat);
        mUnbinder = ButterKnife.bind(this);

        AlamatFragment alamatFragment = (AlamatFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if (alamatFragment == null) {
            alamatFragment = mFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    alamatFragment, R.id.fragment_container);
        }
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }


}
