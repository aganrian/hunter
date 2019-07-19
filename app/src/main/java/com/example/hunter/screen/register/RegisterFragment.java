package com.example.hunter.screen.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.hunter.base.BaseFragment;
import com.example.hunter.custom.CustomDialog;
import com.example.hunter.screen.otp.OtpActivity;
import com.google.android.material.textfield.TextInputEditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import com.example.hunter.R;


/*class fragment*/
public class RegisterFragment extends BaseFragment implements RegisterContract.View {

    @Inject
    RegisterContract.Presenter mPresenterRegister;

    @BindView(R.id.namaRegister)
    TextInputEditText namaRegister;

    @BindView(R.id.emailRegister)
    TextInputEditText emailRegister;

    @BindView(R.id.passRegister)
    TextInputEditText passRegister;

    @BindView(R.id.ly_loading_layout)
    RelativeLayout progressBar;


    @Inject
    public RegisterFragment(){

    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_register;
    }

    @Override
    protected void initView(Bundle state) {
        mPresenterRegister.takeView(this);
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


    /*metode untuk memanggil class otp activity */
    @Override
    public void goToOtpActivity(Integer userId,String namaLengkap,String email) {
        Intent intent = new Intent(parentActivity(),OtpActivity.class);
        intent.putExtra(OtpActivity.USER_ID,userId);
        intent.putExtra(OtpActivity.USER_EMAIL,email);
        intent.putExtra(OtpActivity.USER_NAMA,namaLengkap);
        intent.putExtra(OtpActivity.FROM,RegisterActivity.class.getName());
        startActivity(intent);
    }


    /*ketika tombol register dipanggil*/
    @OnClick(R.id.buttonRegister)
    public void doLogin(){
        mPresenterRegister.doRegister(namaRegister.getText().toString(),emailRegister.getText().toString(),passRegister.getText().toString());
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenterRegister.takeView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenterRegister.dropView();
    }

}
