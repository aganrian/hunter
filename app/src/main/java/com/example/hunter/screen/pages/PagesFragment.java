package com.example.hunter.screen.pages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hunter.base.BaseFragment;
import com.example.hunter.custom.CustomDialog;
import com.example.hunter.screen.forgotpassword.ForgotPasswordActivity;
import com.example.hunter.screen.login.LoginContract;
import com.example.hunter.screen.main.MainActivity;
import com.example.hunter.screen.register.RegisterActivity;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.OnClick;
import id.oase.indonesia.oasebrdiepa.R;

public class PagesFragment extends BaseFragment implements PagesContract.View {

    @Inject
    PagesContract.Presenter mPresenterLogin;

    @BindView(R.id.ly_loading_layout)
    RelativeLayout progressBar;

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.tvContent)
    TextView tvContent;

    @Inject
    @Named("ID_PAGES")
    Integer idPages;

    @Inject
    @Named("TITLE_PAGES")
    String titlePages;


    @Inject
    public PagesFragment(){

    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_pages;
    }

    @Override
    protected void initView(Bundle state) {
        mPresenterLogin.takeView(this);
        mPresenterLogin.getContent(idPages);
        tvTitle.setText(titlePages);
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenterLogin.dropView();
    }

    @Override
    public void setContent(String content) {
        tvContent.setText(content);
    }

    @OnClick(R.id.ibBack)
    public void back(){
        Intent intent =  new Intent(parentActivity(), MainActivity.class);
        intent.putExtra(MainActivity.FROM,4);
        startActivity(intent);
        parentActivity().finishAffinity();
    }

}
