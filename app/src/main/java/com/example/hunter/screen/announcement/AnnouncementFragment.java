package com.example.hunter.screen.announcement;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hunter.adapter.AnnouncementAdapter;
import com.example.hunter.adapter.HistoryReportAdapter;
import com.example.hunter.base.BaseFragment;
import com.example.hunter.data.remote.bean.AnnouncementBean;
import com.example.hunter.data.remote.bean.Historyreportbean;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import id.oase.indonesia.oasebrdiepa.R;

public class AnnouncementFragment extends BaseFragment implements AnnouncementContract.View {

    @Inject
    AnnouncementContract.Presenter mPresenterLogin;

    @BindView(R.id.reportHistoryRecycleView)
    RecyclerView reportHistoryRecycleView;

    @Inject
    AnnouncementAdapter adapter;

    @Inject
    public AnnouncementFragment(){

    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_announcement;
    }

    @Override
    protected void initView(Bundle state) {
        mPresenterLogin.takeView(this);
        mPresenterLogin.getListAnnouncement();
        reportHistoryRecycleView.setLayoutManager(new LinearLayoutManager(parentActivity()));
        reportHistoryRecycleView.setHasFixedSize(true);
        reportHistoryRecycleView.setAdapter(adapter);
    }

    @Override
    public void setListAnnouncement(List<AnnouncementBean.Data.Data2> list) {
        adapter.setGroupList(list);
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
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
