package com.example.hunter.screen.splash;


import com.example.hunter.base.BasePresenter;
import com.example.hunter.base.BaseView;

/*class yang mengatur transaction fitur di dalam splash screen*/
public interface SplashContract {

    interface View extends BaseView<Presenter> {

        void gotoLogin();

        void gotoHome();


    }

    interface Presenter extends BasePresenter<View> {
        void checkUserExisting();
    }
}
