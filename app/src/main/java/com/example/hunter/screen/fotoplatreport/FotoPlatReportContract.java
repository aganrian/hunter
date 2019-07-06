package com.example.hunter.screen.fotoplatreport;


import com.example.hunter.base.BasePresenter;
import com.example.hunter.base.BaseView;
import com.example.hunter.data.remote.bean.OcrBean;

import java.io.File;

public interface FotoPlatReportContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean isShow);

        void showErrorMessage(String message);

        void gotoHistory();

        void gotoSummary(String status, OcrBean.Data data);

    }

    interface Presenter extends BasePresenter<View> {

        void getVehicleReport(Integer vehilceId, String noPolice, File imageFile,String latitude,String longitude);

        void getVehicleReport(String noPolice,File imageFile,String latitude,String longitude);

        void editHandler (Integer vehilceId);

        void getVehicleReportWithHandler(Integer vehilceId,String noPolice,File imageFile,String latitude,String longitude);

        void getUser();

        void kirimManual(String noPolisi);

    }


}
