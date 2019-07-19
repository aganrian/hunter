package com.example.hunter.screen.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hunter.adapter.ListCustomAdapter;
import com.example.hunter.data.remote.bean.ProvinceBean;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.hunter.R;

public class DialogProvince extends Dialog  {

    public DialogProvince(Context context, int themeResId) {
        super(context, themeResId);
    }

    public DialogProvince(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    public Context context;
    public Dialog dialog;

    List<ProvinceBean.Data> listAllResi;

    AutoCompleteTextView edit_noresi;
    private String type = "";
    private String tilteType = "";

    public DialogProvince(Context context, List<ProvinceBean.Data> listAllResi,String type,String tilteType) {
        super(context, R.style.WideDialog);
        this.context = context;
        this.listAllResi = listAllResi;
        this.type = type;
        this.tilteType = tilteType;
    }



    ListCustomAdapter adapter ;

    @BindView(R.id.rv_custom_list)
    RecyclerView recyclerView;

    @BindView(R.id.tv_label)
    TextView tvLabel;

    @BindView(R.id.imageClose)
    ImageView imageClose;

    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_province);
        ButterKnife.bind(this);
        tvLabel.setText(tilteType);
        adapter = new ListCustomAdapter(context,listAllResi,type);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setmItemClickListener(new ListCustomAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String province, Integer idProvince) {
                if(onClickListener!=null){
                    onClickListener.onClickProvince(province,idProvince);
                }
            }
        });

        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }


    public interface OnClickListener{
        void onClickProvince(String provinceName, Integer provinceId);
    }


}
