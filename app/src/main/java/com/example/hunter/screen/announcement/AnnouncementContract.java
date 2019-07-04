package com.example.hunter.screen.announcement;

import com.example.hunter.base.BasePresenter;
import com.example.hunter.base.BaseView;
import com.example.hunter.data.remote.bean.AnnouncementBean;
import com.example.hunter.data.remote.bean.Historyreportbean;

import java.util.List;

public interface AnnouncementContract {


    interface View extends BaseView<AnnouncementContract.Presenter> {
        void setListAnnouncement(List<AnnouncementBean.Data.Data2> list);

        void showErrorMessage(String message);
    }

    interface Presenter extends BasePresenter<AnnouncementContract.View> {

        void getListAnnouncement();

    }

}
