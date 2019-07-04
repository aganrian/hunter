package com.example.hunter.screen.editprofile;


import com.example.hunter.base.BasePresenter;
import com.example.hunter.base.BaseView;
import com.example.hunter.data.local.db.entity.UserEntity;

import java.io.File;

public interface EditProfileContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean isShow);

        void showErrorMessage(String message);

        void setDataProfile(UserEntity userEntity);

        void gotoProfile();

    }

    interface Presenter extends BasePresenter<View> {

        void getDataProfile();

        void updateImage(File imageFile, String type);

        void updateData(String nama, String nomorTelepon,String tanggalLahir,String gender, String noKtp);

    }
}
