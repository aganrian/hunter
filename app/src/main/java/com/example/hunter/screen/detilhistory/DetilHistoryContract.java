package com.example.hunter.screen.detilhistory;


import com.example.hunter.base.BasePresenter;
import com.example.hunter.base.BaseView;

public interface DetilHistoryContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean isShow);

        void showErrorMessage(String message);

        void showDialogSuccess();

    }

    interface Presenter extends BasePresenter<View> {

        void ajukanPerpanjangan (Integer vehicleId);

    }


}
