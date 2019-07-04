package com.example.hunter.screen.otp;


import com.example.hunter.base.BasePresenter;
import com.example.hunter.base.BaseView;

public interface OtpContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean isShow);

        void showErrorMessage(String message);

        void goToIsiProfile();

        void goToChangePassword();

    }

    interface Presenter extends BasePresenter<View> {

        void resendCodeOtp(Integer userId);

        void sendOtp(Integer userId,String otp,String from);


    }
}
