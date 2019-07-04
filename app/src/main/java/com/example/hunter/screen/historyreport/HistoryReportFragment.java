package com.example.hunter.screen.historyreport;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hunter.adapter.HistoryReportAdapter;
import com.example.hunter.base.BaseFragment;
import com.example.hunter.data.remote.bean.Historyreportbean;
import com.example.hunter.screen.detilhistory.DetilHistoryActivity;
import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import id.oase.indonesia.oasebrdiepa.R;

public class HistoryReportFragment extends BaseFragment implements HistoryReportContract.View, HistoryReportAdapter.OnClickListener {

    @Inject
    HistoryReportContract.Presenter mPresenterLogin;

    @BindView(R.id.reportHistoryRecycleView)
    RecyclerView reportHistoryRecycleView;

    @BindView(R.id.edit_search)
    AutoCompleteTextView search;

    @Inject
    HistoryReportAdapter adapter;

    @Inject
    public HistoryReportFragment(){

    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_history_report;
    }

    @Override
    protected void initView(Bundle state) {
        mPresenterLogin.takeView(this);
        search.setHint(getString(R.string.search_no));
        mPresenterLogin.getListHistory();
        reportHistoryRecycleView.setLayoutManager(new LinearLayoutManager(parentActivity()));
        reportHistoryRecycleView.setHasFixedSize(true);
        reportHistoryRecycleView.setAdapter(adapter);
        adapter.setOnClickListener(this);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void setListHistory(List<Historyreportbean.Data> list) {
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


    @Override
    public void OnClickBerita(Historyreportbean.Data beritaBean) {
        Intent intent = new Intent(parentActivity(), DetilHistoryActivity.class);
        intent.putExtra(DetilHistoryActivity.DATA,beritaBean);
        startActivity(intent);
    }


}
