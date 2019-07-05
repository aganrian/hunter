package com.example.hunter.screen.alamat;


import com.example.hunter.base.BasePresenter;
import com.example.hunter.base.BaseView;
import com.example.hunter.data.local.db.entity.UserEntity;
import com.example.hunter.data.remote.bean.ProvinceBean;

import java.util.List;

public interface AlamatContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean isShow);

        void showErrorMessage(String message);

        void setAlamat(UserEntity userEntity);

        void setProvinsi(List<ProvinceBean.Data> provinsi);

        void setKabupaten(List<ProvinceBean.Data> regencies);

        void setKecamatan(List<ProvinceBean.Data> district);

        void setKelurahan(List<ProvinceBean.Data> village);

        void setKodePost(List<ProvinceBean.Data> kodepos);

        void setPerubahan();
    }

    interface Presenter extends BasePresenter<View> {

        void getAlamat();

        void getProvinsi();

        void getKabupaten(Integer provinceId);

        void getDistrict(Integer regencyId);

        void getVillage(Integer districtId);

        void getPostalCode(Integer villageId);

        void simpanPerubahan(String province,Integer provinceId,
                             String kabupaten, Integer kabupatenId,
                             String kecamatan, Integer kecamatanId,
                             String kelurahan, Integer kelurahanId,
                             String postcode, Integer postcodeId,String alamat);
    }
}
