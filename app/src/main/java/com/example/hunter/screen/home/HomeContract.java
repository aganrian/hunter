package com.example.hunter.screen.home;

import com.example.hunter.base.BasePresenter;
import com.example.hunter.base.BaseView;
import com.example.hunter.data.remote.bean.ProductBean;
import com.example.hunter.data.remote.bean.UserBean;
import com.example.hunter.screen.login.LoginContract;

import java.util.List;

public interface HomeContract {

    interface View extends BaseView<HomeContract.Presenter> {

        void setLoadingIndicator(boolean isShow);

        void showErrorMessage(String message);

        void setHomeInformation(String urlPicture,String nama,Integer point);

        void updatePoint(String point);

        void setListProduct(List<ProductBean.Data> data);

        void setListProductRefresh(List<ProductBean.Data> data);

        void setHomeInformationRefresh(UserBean userBean);

    }

    interface Presenter extends BasePresenter<HomeContract.View> {

        void getHomeInformation();

        void getProductAll();

        void getProductAllRefreseh();

        void productReadem(Integer productId);

        void getUser();
    }

}
