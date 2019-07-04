package com.example.hunter.screen.profile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.hunter.adapter.ReviewContentPagerAdapter;
import com.example.hunter.base.BaseFragment;
import com.example.hunter.screen.editprofile.EditProfileActivity;
import com.example.hunter.screen.history.HistoryContract;
import com.example.hunter.screen.historyredeem.HistoryRedeemFragment;
import com.example.hunter.screen.historyreport.HistoryReportFragment;
import com.example.hunter.screen.login.LoginActivity;
import com.example.hunter.screen.main.MainActivity;
import com.example.hunter.utils.ImageUtils;
import com.google.android.material.tabs.TabLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import id.oase.indonesia.oasebrdiepa.BuildConfig;
import id.oase.indonesia.oasebrdiepa.R;

public class ProfileFragment extends BaseFragment implements ProfileContract.View {

    @Inject
    ProfileContract.Presenter mPresenterLogin;

    @BindView(R.id.name)
    TextView textName;

    @BindView(R.id.noHp)
    TextView noHp;

    @BindView(R.id.ivImage)
    CircleImageView imageView;

    private Integer subFrom;

    @Inject
    public ProfileFragment(){

    }



    @Override
    protected int getLayoutView() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void initView(Bundle state) {
        mPresenterLogin.takeView(this);

    }


    @Override
    public void onResume() {
        super.onResume();
        mPresenterLogin.takeView(this);
        mPresenterLogin.getHomeInformation();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenterLogin.dropView();
    }

    @Override
    public void setHomeInformation(String urlPicture, String nama, String hp) {
        parentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ImageUtils.displayImageFromUrl(parentActivity(),imageView,
                        BuildConfig.API_URL_IMAGE_PROFILE.concat(urlPicture),null,R.drawable.ic_person);
            }
        });
        textName.setText(nama);
        noHp.setText(hp);

    }

    @OnClick(R.id.btnBatalManual)
    public void logout(){
        mPresenterLogin.logout();
    }

    @Override
    public void goToLogin() {
        startActivity(new Intent(parentActivity(), LoginActivity.class));
        parentActivity().finishAffinity();
    }

    @OnClick(R.id.menuProfile)
    public void menuProfile(){
        startActivity(new Intent(parentActivity(), EditProfileActivity.class));
    }
}
