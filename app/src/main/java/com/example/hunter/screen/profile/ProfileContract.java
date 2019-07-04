package com.example.hunter.screen.profile;

import com.example.hunter.base.BasePresenter;
import com.example.hunter.base.BaseView;

public interface ProfileContract {


    interface View extends BaseView<ProfileContract.Presenter> {

        void setHomeInformation(String urlPicture,String nama,String point);

        void goToLogin();

    }

    interface Presenter extends BasePresenter<ProfileContract.View> {

        void getHomeInformation();

        void logout();

    }

}
