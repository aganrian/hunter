package com.example.hunter.screen.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.example.hunter.base.BaseFragment;
import com.example.hunter.custom.CustomDialog;
import com.example.hunter.screen.forgotpassword.ForgotPasswordActivity;
import com.example.hunter.screen.main.MainActivity;
import com.example.hunter.screen.register.RegisterActivity;
import com.google.android.material.textfield.TextInputEditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import com.example.hunter.R;

/*fragment Login */
public class LoginFragment extends BaseFragment implements LoginContract.View {

    @Inject
    LoginContract.Presenter mPresenterLogin;

    @BindView(R.id.edEmail)
    TextInputEditText edEmail;

    @BindView(R.id.edPass)
    TextInputEditText edPassword;

    @BindView(R.id.ly_loading_layout)
    RelativeLayout progressBar;


    @Inject
    public LoginFragment(){

    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_login;
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
    public void gotoHomeActivity() {
       Intent intent =  new Intent(parentActivity(), MainActivity.class);
        startActivity(intent);
        parentActivity().finishAffinity();
    }

    /*action ketika tombol login di tekan*/
    @OnClick(R.id.loginbtn)
    public void doLogin(){
        mPresenterLogin.doLogin(edEmail.getText().toString(),edPassword.getText().toString());
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenterLogin.takeView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenterLogin.dropView();
    }

    /*action ketika tombool register di tekan*/
    @OnClick(R.id.buttonRegister)
    public void register(){
        startActivity(new Intent(parentActivity(), RegisterActivity.class));
    }

    /*action ketika tombol lupa password di tekan*/
    @OnClick(R.id.idLupaPassword)
    public void lupaPassword(){
        startActivity(new Intent(parentActivity(), ForgotPasswordActivity.class));
    }
}
