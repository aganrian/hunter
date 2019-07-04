package com.example.hunter.screen.changepassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.hunter.base.BaseFragment;
import com.example.hunter.screen.forgotpassword.ForgotPasswordActivity;
import com.example.hunter.screen.forgotpassword.ForgotPasswordContract;
import com.example.hunter.screen.login.LoginActivity;
import com.example.hunter.screen.otp.OtpActivity;
import com.google.android.material.textfield.TextInputEditText;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.OnClick;
import id.oase.indonesia.oasebrdiepa.R;

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
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
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

    @Override
    public void goToLogin() {
        Intent intent = new Intent(parentActivity(), LoginActivity.class);
        startActivity(intent);
        parentActivity().finishAffinity();
    }

    @OnClick(R.id.changePass)
    public void changePass(){
        mPresenterOtp.changePassword(idUser,edPassword.getText().toString(),edPassConfirm.getText().toString());
    }
}
