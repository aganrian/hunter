package com.example.hunter.screen.detilhistory;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.hunter.screen.fotoplatreport.FotoPlatReportFragment;
import com.example.hunter.utils.ActivityUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;
import id.oase.indonesia.oasebrdiepa.R;

public class DetilHistoryActivity extends DaggerAppCompatActivity {

    public static String DATA = "DATA";
    @Inject
    DetilHistoryFragment mFragment;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detilhistory);
        mUnbinder = ButterKnife.bind(this);

        DetilHistoryFragment detilHistoryFragment = (DetilHistoryFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if (detilHistoryFragment == null) {
            detilHistoryFragment = mFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    detilHistoryFragment, R.id.fragment_container);
        }
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }


}
