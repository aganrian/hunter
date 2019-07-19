package com.example.hunter.screen.editprofile;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.hunter.screen.login.LoginFragment;
import com.example.hunter.utils.ActivityUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;
import com.example.hunter.R;

/*ini edit profile activity*/
public class EditProfileActivity extends DaggerAppCompatActivity {


    @Inject
    EditProfileFragment mFragment;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_p);
        mUnbinder = ButterKnife.bind(this);

        EditProfileFragment editProfileFragment = (EditProfileFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if (editProfileFragment == null) {
            editProfileFragment = mFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    editProfileFragment, R.id.fragment_container);
        }
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }


}
