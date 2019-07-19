package com.example.hunter.screen.historyredeem;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hunter.adapter.HistoryReedemAdapter;
import com.example.hunter.adapter.HistoryReportAdapter;
import com.example.hunter.base.BaseFragment;
import com.example.hunter.data.remote.bean.HistoryRedeembean;
import com.example.hunter.data.remote.bean.Historyreportbean;
import com.example.hunter.screen.historyreport.HistoryReportContract;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import com.example.hunter.R;

/*fragment history redeem fragment list*/
public class HistoryRedeemFragment extends BaseFragment implements HistoryRedeemContract.View {

    @Inject
    HistoryRedeemContract.Presenter mPresenterLogin;

    @BindView(R.id.reportHistoryRecycleView)
    RecyclerView reportHistoryRecycleView;

    @BindView(R.id.edit_search)
    AutoCompleteTextView search;

    @Inject
    HistoryReedemAdapter adapter;

    @Inject
    public HistoryRedeemFragment(){

    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_history_report;
    }

    @Override
    protected void initView(Bundle state) {
        mPresenterLogin.takeView(this);
        search.setHint(getString(R.string.search_product));
        mPresenterLogin.getListHistory();
        reportHistoryRecycleView.setLayoutManager(new LinearLayoutManager(parentActivity()));
        reportHistoryRecycleView.setHasFixedSize(true);
        reportHistoryRecycleView.setAdapter(adapter);

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
    public void setListHistory(List<HistoryRedeembean.Data> list) {
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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) { // fragment is visible and have created
            mPresenterLogin.getListHistory();
        }
    }


}
