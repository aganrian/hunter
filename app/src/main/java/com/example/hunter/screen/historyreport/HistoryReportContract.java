package com.example.hunter.screen.historyreport;

import com.example.hunter.base.BasePresenter;
import com.example.hunter.base.BaseView;
import com.example.hunter.data.remote.bean.Historyreportbean;

import java.util.List;

public interface HistoryReportContract {


    interface View extends BaseView<HistoryReportContract.Presenter> {
        void setListHistory(List<Historyreportbean.Data> list);

        void showErrorMessage(String message);
    }

    interface Presenter extends BasePresenter<HistoryReportContract.View> {

        void getListHistory ();

    }

}
