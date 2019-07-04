package com.example.hunter.screen.historyredeem;

import com.example.hunter.base.BasePresenter;
import com.example.hunter.base.BaseView;
import com.example.hunter.data.remote.bean.HistoryRedeembean;
import com.example.hunter.data.remote.bean.Historyreportbean;

import java.util.List;

public interface HistoryRedeemContract {


    interface View extends BaseView<HistoryRedeemContract.Presenter> {
        void setListHistory(List<HistoryRedeembean.Data> list);

        void showErrorMessage(String message);
    }

    interface Presenter extends BasePresenter<HistoryRedeemContract.View> {

        void getListHistory();

    }

}
