package com.example.hunter.screen.forgotpassword;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hunter.base.BaseFragment;
import com.example.hunter.custom.CustomDialog;
import com.example.hunter.screen.biodata.BiodataActivity;
import com.example.hunter.screen.otp.OtpActivity;
import com.example.hunter.screen.otp.OtpContract;
import com.example.hunter.screen.register.RegisterActivity;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.OnClick;
import com.example.hunter.R;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class ForgotPasswordFragment extends BaseFragment implements ForgotPasswordContract.View {

    @Inject
    ForgotPasswordContract.Presenter mPresenterOtp;

    @BindView(R.id.ly_loading_layout)
    RelativeLayout progressBar;

    @BindView(R.id.edEmail)
    EditText edEmail;


    @Inject
    public ForgotPasswordFragment(){

    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_forgot;
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

    /*action ketika berhasil lupa password di arahkan ke otp dahulu*/
    @Override
    public void setForgotPassword(Integer userId, String email) {
        Intent intent = new Intent(parentActivity(), OtpActivity.class);
        intent.putExtra(OtpActivity.USER_ID,userId);
        intent.putExtra(OtpActivity.USER_EMAIL,email);
        intent.putExtra(OtpActivity.FROM, ForgotPasswordActivity.class.getName());
        intent.putExtra(OtpActivity.USER_NAMA,"");
        startActivity(intent);
    }

    /*action ketika memanggil lupa password*/
    @OnClick(R.id.nextOtp)
    public void toOtp(){
        mPresenterOtp.callForgotPassword(edEmail.getText().toString());
    }
}
