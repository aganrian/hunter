package com.example.hunter.screen.changepassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.hunter.base.BaseFragment;
import com.example.hunter.custom.CustomDialog;
import com.example.hunter.screen.forgotpassword.ForgotPasswordActivity;
import com.example.hunter.screen.forgotpassword.ForgotPasswordContract;
import com.example.hunter.screen.login.LoginActivity;
import com.example.hunter.screen.otp.OtpActivity;
import com.google.android.material.textfield.TextInputEditText;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.OnClick;
import com.example.hunter.R;

public class ChangePasswordFragment extends BaseFragment implements ChangePasswordContract.View {

    @Inject
    @Named("USER_ID_CHANGE")
    Integer idUser;

    @Inject
    @Named("USER_EMAIL_CHANGE")
    String userEmail;

    @Inject
    ChangePasswordContract.Presenter mPresenterOtp;

    @BindView(R.id.ly_loading_layout)
    RelativeLayout progressBar;

    @BindView(R.id.edPass)
    TextInputEditText edPassword;

    @BindView(R.id.edPassConfirm)
    TextInputEditText edPassConfirm;


    @Inject
    public ChangePasswordFragment(){

    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_changepassword;
    }

    @Override
    protected void initView(Bundle state) {
        mPresenterOtp.takeView(this);

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
        mPresenterOtp.takeView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenterOtp.dropView();
    }

    /*ketika berhasil ganti login lgsg di suruh login ulang*/
    @Override
    public void goToLogin() {
        Intent intent = new Intent(parentActivity(), LoginActivity.class);
        startActivity(intent);
        parentActivity().finishAffinity();
    }

    /*action ketika ganti passsword*/
    @OnClick(R.id.changePass)
    public void changePass(){
        mPresenterOtp.changePassword(idUser,edPassword.getText().toString(),edPassConfirm.getText().toString());
    }
}
