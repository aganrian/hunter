package com.example.hunter.screen.pages;


import com.example.hunter.base.BasePresenter;
import com.example.hunter.base.BaseView;

public interface PagesContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean isShow);

        void showErrorMessage(String message);

        void setContent(String content);

    }

    interface Presenter extends BasePresenter<View> {

        void getContent(Integer id);

    }
}
