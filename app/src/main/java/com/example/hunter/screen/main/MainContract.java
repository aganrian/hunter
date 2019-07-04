package com.example.hunter.screen.main;

import com.example.hunter.base.BasePresenter;
import com.example.hunter.base.BaseView;

public interface MainContract {

    interface View extends BaseView<Presenter> {
        void showErrorMessage(String message);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
