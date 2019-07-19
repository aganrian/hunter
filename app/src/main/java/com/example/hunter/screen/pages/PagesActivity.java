package com.example.hunter.screen.pages;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.hunter.screen.login.LoginFragment;
import com.example.hunter.utils.ActivityUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;
import com.example.hunter.R;

/*activity pages */
public class PagesActivity extends DaggerAppCompatActivity {

    public static String TITLE_PAGES = "TITLE_PAGES";
    public static String ID_PAGES = "ID_PAGES";

    @Inject
    PagesFragment mFragment;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pages);
        mUnbinder = ButterKnife.bind(this);

        PagesFragment pagesFragment = (PagesFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if (pagesFragment == null) {
            pagesFragment = mFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    pagesFragment, R.id.fragment_container);
        }
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }


}
