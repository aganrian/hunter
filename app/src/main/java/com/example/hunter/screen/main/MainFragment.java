package com.example.hunter.screen.main;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.hunter.adapter.ViewPagerAdapter;
import com.example.hunter.base.BaseFragment;
import com.example.hunter.screen.announcement.AnnouncementFragment;
import com.example.hunter.screen.fotoplat.FotoPlatFragment;
import com.example.hunter.screen.history.HistoryFragment;
import com.example.hunter.screen.historyreport.HistoryReportFragment;
import com.example.hunter.screen.home.HomeFragment;
import com.example.hunter.screen.profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import javax.inject.Inject;
import butterknife.BindView;
import id.oase.indonesia.oasebrdiepa.R;

public class MainFragment extends BaseFragment implements MainContract.View{

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    //This is our viewPager
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    MenuItem prevMenuItem;

    @Inject
    MainContract.Presenter mPresenter;

    private Integer from,subFrom = 0;

    @Inject
    public MainFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getArguments() != null && getArguments().containsKey(MainActivity.FROM)) {
            from = getArguments().getInt(MainActivity.FROM);
        }
        if (getArguments() != null && getArguments().containsKey(MainActivity.SUBFROM)) {
            subFrom = getArguments().getInt(MainActivity.SUBFROM);
        }
    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initView(Bundle state) {
        mPresenter.takeView(this);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.nav_home:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.nav_history:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.nav_report:
                                viewPager.setCurrentItem(2);
                                break;
                            case R.id.nav_message:
                                viewPager.setCurrentItem(3);
                                break;

                            case R.id.nav_profile:
                                viewPager.setCurrentItem(4);
                                break;

                        }
                        return false;
                    }
                });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }
                else
                {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        setupViewPager(viewPager);


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        HomeFragment homeFragment = new HomeFragment();
        HistoryFragment historyFragment = new HistoryFragment();
        AnnouncementFragment announcementFragment = new AnnouncementFragment();
        FotoPlatFragment fotoPlatFragment =   new FotoPlatFragment();
        adapter.addFragment(homeFragment);
        Bundle bundle = new Bundle();
        bundle.putInt(MainActivity.SUBFROM, subFrom);
        historyFragment.setArguments(bundle);
        adapter.addFragment(historyFragment);
        adapter.addFragment(fotoPlatFragment);
        adapter.addFragment(announcementFragment);
        ProfileFragment profileFragment = new ProfileFragment();
        adapter.addFragment(profileFragment);
        viewPager.setAdapter(adapter);
        if(from==null){
            viewPager.setCurrentItem(0);
        }else{
            viewPager.setCurrentItem(from);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        mPresenter.dropView();
        super.onDestroyView();
    }


}
