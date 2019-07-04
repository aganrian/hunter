package com.example.hunter.screen.editprofile;


import android.os.Bundle;

import com.example.hunter.di.ActivityScoped;
import com.example.hunter.di.FragmentScoped;
import com.example.hunter.screen.login.LoginActivity;
import com.example.hunter.screen.login.LoginContract;
import com.example.hunter.screen.login.LoginFragment;
import com.example.hunter.screen.login.LoginPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class EditProfileModule {

    @ActivityScoped
    @Binds
    abstract EditProfileContract.Presenter editProfilePresenter(EditProfilePresenter presenter);

    @FragmentScoped
    @ContributesAndroidInjector
    abstract EditProfileFragment editProfileFragment();

    @Provides
    @ActivityScoped
    static Bundle provideExtras(EditProfileActivity activity) {
        return activity.getIntent().getExtras();
    }

}