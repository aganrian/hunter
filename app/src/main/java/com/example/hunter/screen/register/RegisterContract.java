package com.example.hunter.screen.register;


import com.example.hunter.base.BasePresenter;
import com.example.hunter.base.BaseView;

/*class contract yang akan mengatur transaksi di dalam register jadi jika ada mw koneksi ke data/model silahkan
* tambahkan ke presenter, tapi jika dari presenter ingin melakukan view tambahkan ke View*/
public interface RegisterContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean isShow);

        void showErrorMessage(String message);

        void goToOtpActivity(Integer userId,String namaLengkap,String email);

    }

    interface Presenter extends BasePresenter<View> {

        void doRegister(String nama, String email, String password);

    }
}
