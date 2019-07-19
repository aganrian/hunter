package com.example.hunter.screen.history;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.hunter.adapter.ProductAdapter;
import com.example.hunter.adapter.ReviewContentPagerAdapter;
import com.example.hunter.adapter.ViewPagerAdapter;
import com.example.hunter.base.BaseFragment;
import com.example.hunter.data.remote.bean.ProductBean;
import com.example.hunter.screen.historyredeem.HistoryRedeemFragment;
import com.example.hunter.screen.historyreport.HistoryReportFragment;
import com.example.hunter.screen.home.HomeContract;
import com.example.hunter.screen.main.MainActivity;
import com.example.hunter.utils.ImageUtils;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import com.example.hunter.BuildConfig;
import com.example.hunter.R;


public class HistoryFragment extends BaseFragment implements HistoryContract.View {

    @Inject
    HistoryContract.Presenter mPresenterLogin;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    private Integer subFrom;

    @Inject
    public HistoryFragment(){

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (getArguments() != null && getArguments().containsKey(MainActivity.SUBFROM)) {
            subFrom = getArguments().getInt(MainActivity.SUBFROM);
        }
    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_history;
    }

    @Override
    protected void initView(Bundle state) {
        mPresenterLogin.takeView(this);
        parentActivity().setSupportActionBar(toolbar);
        ActionBar mActionBar = parentActivity().getSupportActionBar();
        assert mActionBar != null;
        mActionBar.setDisplayShowTitleEnabled(true);
        mActionBar.setTitle(getString(R.string.rwyt));


        initTab(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#5D5D5D"));
        tabLayout.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(Color.parseColor("#000000"), Color.parseColor("#000000"));

    }

    private void initTab(ViewPager viewPager) {
        ReviewContentPagerAdapter adapter = new ReviewContentPagerAdapter(getChildFragmentManager());

        HistoryReportFragment historyReportFragment = new HistoryReportFragment();
        HistoryRedeemFragment historyRedeemFragment = new HistoryRedeemFragment();
        adapter.addFragment(historyReportFragment,getString(R.string.laporan));
        adapter.addFragment(historyRedeemFragment,getString(R.string.redeem));
        viewPager.setAdapter(adapter);

        if(subFrom==null || subFrom == 0){
            viewPager.setCurrentItem(0);
        }else{
            viewPager.setCurrentItem(1);
        }
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


}
