package com.example.hunter.screen.history;

import com.example.hunter.base.BasePresenter;
import com.example.hunter.base.BaseView;
import com.example.hunter.data.remote.bean.ProductBean;
import com.example.hunter.screen.home.HomeContract;

import java.util.List;

public interface HistoryContract {


    interface View extends BaseView<HistoryContract.Presenter> {


    }

    interface Presenter extends BasePresenter<HistoryContract.View> {



    }

}
