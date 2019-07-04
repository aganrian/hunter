package com.example.hunter.screen.login;


import com.example.hunter.base.BasePresenter;
import com.example.hunter.base.BaseView;

public interface LoginContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean isShow);

        void showErrorMessage(String message);

        void gotoHomeActivity();

    }

    interface Presenter extends BasePresenter<View> {

        void doLogin(String phone, String password);

    }
}
