package com.example.hunter.screen.register;


import com.example.hunter.base.BasePresenter;
import com.example.hunter.base.BaseView;

public interface RegisterContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean isShow);

        void showErrorMessage(String message);

        void goToOtpActivity(Integer userId,String namaLengkap,String email);

    }

    interface Presenter extends BasePresenter<View> {

        void doLogin(String nama,String email, String password);

    }
}
