package com.example.hunter.screen.fotoplatreport;


import com.example.hunter.base.BasePresenter;
import com.example.hunter.base.BaseView;
import com.example.hunter.data.remote.bean.OcrBean;

public interface FotoPlatReportContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean isShow);

        void showErrorMessage(String message);

        void gotoHistory();

        void gotoSummary(String status, OcrBean.Data data);

        void showPenawaranMitra(Integer partnerId);

    }

    interface Presenter extends BasePresenter<View> {

        void getVehicleReport(Integer vehilceId,String noPolice);

        void getVehicleReport(String noPolice);

        void editHandler (Integer vehilceId);

        void getVehicleReportWithHandler(Integer vehilceId,String noPolice);

        void getUser();

        void kirimManual(String noPolisi);

    }


}
