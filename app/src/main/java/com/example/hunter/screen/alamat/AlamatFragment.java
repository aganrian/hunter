package com.example.hunter.screen.alamat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.hunter.base.BaseFragment;
import com.example.hunter.custom.CustomDialog;
import com.example.hunter.data.local.db.entity.UserEntity;
import com.example.hunter.data.remote.bean.ProvinceBean;
import com.example.hunter.screen.dialog.DialogProvince;
import com.example.hunter.screen.forgotpassword.ForgotPasswordActivity;
import com.example.hunter.screen.login.LoginContract;
import com.example.hunter.screen.main.MainActivity;
import com.example.hunter.screen.register.RegisterActivity;
import com.example.hunter.utils.constant.S;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import id.oase.indonesia.oasebrdiepa.R;

public class AlamatFragment extends BaseFragment implements AlamatContract.View {

    @Inject
    AlamatContract.Presenter mPresenterLogin;

    @BindView(R.id.ly_loading_layout)
    RelativeLayout progressBar;

    @BindView(R.id.valueProvince)
    EditText valueProvince;

    @BindView(R.id.valueKabupaten)
    EditText valueKabupaten;

    @BindView(R.id.valueKecamatan)
    EditText valueKecamatan;

    @BindView(R.id.valueKelurahan)
    EditText valueKelurahan;

    @BindView(R.id.valueKodepos)
    EditText valueKodepos;

    @BindView(R.id.valueAlamat)
    EditText valueAlamat;

    private Integer provinceId = 0,kabupatenId = 0,kecamatanId = 0,kelurahanId = 0,kodeposId = 0;

    @Inject
    public AlamatFragment(){

    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_alamat;
    }

    @Override
    protected void initView(Bundle state) {
        mPresenterLogin.takeView(this);
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        progressBar.setVisibility(active ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showErrorMessage(String message) {
        CustomDialog customDialog = new CustomDialog();
        customDialog.showDialog(parentActivity(),"",message,"",
                false,false,false);
        customDialog.setOnDialogResultListener(new CustomDialog.OnDialogClickBtnListener() {
            @Override
            public void onPositiveLisneter() {

            }

            @Override
            public void onNegativeListener() {

            }

            @Override
            public void onOkListener() {

            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        mPresenterLogin.takeView(this);
        mPresenterLogin.getAlamat();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenterLogin.dropView();
    }

    @Override
    public void setAlamat(UserEntity userEntity) {

        valueProvince.setText(userEntity.getProvinsi());
        valueKecamatan.setText(userEntity.getKecamatan());
        valueKabupaten.setText(userEntity.getKabupaten());
        valueKelurahan.setText(userEntity.getKelurahan());
        valueKodepos.setText(userEntity.getKodepos());
        valueAlamat.setText(userEntity.getAlamat());

        if(userEntity.getProvinsi_id()!=null){
            provinceId = userEntity.getProvinsi_id();
        }

        if(userEntity.getKabupaten_id()!=null){
            kabupatenId = userEntity.getKabupaten_id();
        }

        if(userEntity.getKecamatan_id()!=null){
            kecamatanId = userEntity.getKecamatan_id();
        }

        if(userEntity.getKelurahan_id()!=null){
            kelurahanId = userEntity.getKelurahan_id();
        }

        if(userEntity.getKodepos_id()!=null){
            kodeposId = userEntity.getKodepos_id();
        }
    }

    @OnClick(R.id.ibBack)
    public void back(){
        Intent intent =  new Intent(parentActivity(), MainActivity.class);
        intent.putExtra(MainActivity.FROM,4);
        startActivity(intent);
        parentActivity().finishAffinity();
    }


    private DialogProvince dialogProvince;



    @Override
    public void setProvinsi(List<ProvinceBean.Data> provinsi) {
        dialogProvince = new DialogProvince(parentActivity(),provinsi, S.PROVINCE,parentActivity().getString(R.string.pilih_provinsi));
        dialogProvince.setOnClickListener(new DialogProvince.OnClickListener() {
            @Override
            public void onClickProvince(String provinceName, Integer provinceIdChoose) {
                valueProvince.setText(provinceName);
                provinceId = provinceIdChoose;
                kabupatenId = 0;
                valueKabupaten.setText(getString(R.string.chooseRegency));
                kecamatanId = 0;
                valueKecamatan.setText(getString(R.string.choose_district));
                kelurahanId = 0;
                valueKelurahan.setText(getString(R.string.choose_village));
                kodeposId = 0;
                valueKodepos.setText(getString(R.string.choose_kodepos));
                dialogProvince.dismiss();
            }
        });
        dialogProvince.show();
        dialogProvince.setCanceledOnTouchOutside(true);
    }



    @OnClick(R.id.valueProvince)
    public void onProvinsi(){
        mPresenterLogin.getProvinsi();
    }

    @OnClick(R.id.valueKabupaten)
    public void onKabupaten(){
        if(provinceId == 0){
            showErrorMessage(getString(R.string.error_province));
        }else{
            mPresenterLogin.getKabupaten(provinceId);
        }
    }


    @Override
    public void setKabupaten(List<ProvinceBean.Data> regencies) {
        dialogProvince = new DialogProvince(parentActivity(),regencies,S.REGENCY,getString(R.string.pilih_regency));
        dialogProvince.setOnClickListener(new DialogProvince.OnClickListener() {
            @Override
            public void onClickProvince(String regencyName, Integer regencyId) {
                kabupatenId = regencyId;
                valueKabupaten.setText(regencyName);
                kecamatanId = 0;
                valueKecamatan.setText(getString(R.string.choose_district));
                kelurahanId = 0;
                valueKelurahan.setText(getString(R.string.choose_village));
                kodeposId = 0;
                valueKodepos.setText(getString(R.string.choose_kodepos));
                dialogProvince.dismiss();
            }
        });
        dialogProvince.show();
        dialogProvince.setCanceledOnTouchOutside(true);
    }

    @OnClick(R.id.valueKecamatan)
    public void onClickKecamatan(){
        if(provinceId == 0){
            showErrorMessage(getString(R.string.error_province));
        }else if(kabupatenId == 0){
            showErrorMessage(getString(R.string.error_regency));
        }else{
            mPresenterLogin.getDistrict(kabupatenId);
        }
    }

    @Override
    public void setKecamatan(List<ProvinceBean.Data> district) {
        dialogProvince = new DialogProvince(parentActivity(),district,S.DISTRICT,getString(R.string.pilih_kecamatan));
        dialogProvince.setOnClickListener(new DialogProvince.OnClickListener() {
            @Override
            public void onClickProvince(String districtName, Integer districtId) {
                kecamatanId = districtId;
                valueKecamatan.setText(districtName);
                kelurahanId = 0;
                valueKelurahan.setText(getString(R.string.choose_village));
                kodeposId = 0;
                valueKodepos.setText(getString(R.string.choose_kodepos));
                dialogProvince.dismiss();
            }
        });
        dialogProvince.show();
        dialogProvince.setCanceledOnTouchOutside(true);
    }

    @OnClick(R.id.valueKelurahan)
    public void onClickKelurahan(){
        if(provinceId == 0){
            showErrorMessage(getString(R.string.error_province));
        }else if(kabupatenId == 0){
            showErrorMessage(getString(R.string.error_regency));
        }else if(kecamatanId == 0){
            showErrorMessage(getString(R.string.errro_district));
        }else{
            mPresenterLogin.getVillage(kecamatanId);
        }
    }

    @Override
    public void setKelurahan(List<ProvinceBean.Data> village) {
        dialogProvince = new DialogProvince(parentActivity(),village,S.VILLAGE,getString(R.string.pilihKelurahan));
        dialogProvince.setOnClickListener(new DialogProvince.OnClickListener() {
            @Override
            public void onClickProvince(String villageName, Integer villageId) {
                kelurahanId = villageId;
                valueKelurahan.setText(villageName);
                kodeposId = 0;
                valueKodepos.setText(getString(R.string.choose_kodepos));
                dialogProvince.dismiss();
            }
        });
        dialogProvince.show();
        dialogProvince.setCanceledOnTouchOutside(true);
    }

    @OnClick(R.id.valueKodepos)
    public void kodePost(){
        if(provinceId == 0){
            showErrorMessage(getString(R.string.error_province));
        }else if(kabupatenId == 0){
            showErrorMessage(getString(R.string.error_regency));
        }else if(kecamatanId == 0){
            showErrorMessage(getString(R.string.errro_district));
        }else if(kelurahanId == 0){
            showErrorMessage(getString(R.string.error_village));
        }else{
            mPresenterLogin.getPostalCode(kelurahanId);
        }
    }

    @Override
    public void setKodePost(List<ProvinceBean.Data> kodepos) {
        dialogProvince = new DialogProvince(parentActivity(),kodepos,S.POSTCODE,getString(R.string.pilihkodepos));
        dialogProvince.setOnClickListener(new DialogProvince.OnClickListener() {
            @Override
            public void onClickProvince(String kodepostName, Integer kodepostId) {
                kodeposId = kodepostId;
                valueKodepos.setText(kodepostName);
                dialogProvince.dismiss();
            }
        });
        dialogProvince.show();
        dialogProvince.setCanceledOnTouchOutside(true);
    }

    @OnClick(R.id.btnSimpan)
    public void btnSimpan(){
        mPresenterLogin.simpanPerubahan(valueProvince.getText().toString(),provinceId,
                valueKabupaten.getText().toString(),kabupatenId,
                valueKecamatan.getText().toString(),kecamatanId,
                valueKelurahan.getText().toString(),kelurahanId,
                valueKodepos.getText().toString(),kodeposId,valueAlamat.getText().toString());
    }

    @Override
    public void setPerubahan() {
        Intent intent =  new Intent(parentActivity(), MainActivity.class);
        intent.putExtra(MainActivity.FROM,4);
        startActivity(intent);
        parentActivity().finishAffinity();
    }
}
