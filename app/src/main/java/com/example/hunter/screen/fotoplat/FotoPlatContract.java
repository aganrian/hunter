package com.example.hunter.screen.fotoplat;


import com.example.hunter.base.BasePresenter;
import com.example.hunter.base.BaseView;
import com.example.hunter.data.remote.bean.OcrBean;

import java.io.File;

public interface FotoPlatContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean isShow);

        void showErrorMessage(String message);

        void gotoSummary(String status, OcrBean.Data data);

        void showInputManual();

    }

    interface Presenter extends BasePresenter<View> {
        void kirimOcr(File file);

        void kirimManual(String noPolisi);

    }
}
