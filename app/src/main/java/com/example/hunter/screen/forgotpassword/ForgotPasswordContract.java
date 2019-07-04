package com.example.hunter.screen.forgotpassword;


import com.example.hunter.base.BasePresenter;
import com.example.hunter.base.BaseView;

public interface ForgotPasswordContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean isShow);

        void showErrorMessage(String message);

        void setForgotPassword(Integer userId, String email);

    }

    interface Presenter extends BasePresenter<View> {


        void callForgotPassword(String email);


    }
}
