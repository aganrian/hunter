package com.example.hunter.screen.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.hunter.utils.ActivityUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;
import id.oase.indonesia.oasebrdiepa.R;

public class MainActivity extends DaggerAppCompatActivity {

    public static String FROM = "FROM";
    public static String SUBFROM = "SUBFROM";

    @Inject
    MainFragment mFragment;

    private Unbinder mUnbinder;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mUnbinder = ButterKnife.bind(this);


        MainFragment mainFragment = (MainFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if (mainFragment == null) {
            mainFragment = mFragment;
            if(getIntent().getExtras()==null){
                ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                        mainFragment, R.id.fragment_container);
            }else{
                ActivityUtils.addFragmentToActivityWithBundle(getSupportFragmentManager(),
                        mainFragment, R.id.fragment_container,getIntent().getExtras());
            }

        }
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }


    private boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.doubleBackPressConfirmation, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}
