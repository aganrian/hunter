package com.example.hunter.screen.changepassword;


import com.example.hunter.base.BasePresenter;
import com.example.hunter.base.BaseView;

public interface ChangePasswordContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean isShow);

        void showErrorMessage(String message);

        void goToLogin();

    }

    interface Presenter extends BasePresenter<View> {

        void changePassword(Integer userId,String pass, String confirmPass);

    }
}
