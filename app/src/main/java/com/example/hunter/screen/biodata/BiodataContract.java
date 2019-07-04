package com.example.hunter.screen.biodata;


import com.example.hunter.base.BasePresenter;
import com.example.hunter.base.BaseView;

import java.io.File;

public interface BiodataContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean isShow);

        void showErrorMessage(String message);

        void gotoHomeActivity();

    }

    interface Presenter extends BasePresenter<View> {

        void doNext(Integer userId,String otp,String nama, String tanggalLahir, String gender, String email, String noKtp, File profile, File ktp);

        void getUser(Integer userId);

    }
}
