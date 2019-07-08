package com.example.hunter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hunter.data.remote.bean.HistoryRedeembean;
import com.example.hunter.data.remote.bean.Historyreportbean;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.oase.indonesia.oasebrdiepa.R;

public class HistoryReportAdapter extends RecyclerView.Adapter<HistoryReportAdapter.ItemVH> {

    private List<Historyreportbean.Data> productBeans;
    private ArrayList<Historyreportbean.Data> arraylist;
    private Context context;

    @Inject
    public HistoryReportAdapter() {
        productBeans = new ArrayList<>();
    }

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public HistoryReportAdapter.ItemVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View root = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history_item_layout, viewGroup, false);
        return new HistoryReportAdapter.ItemVH(root);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryReportAdapter.ItemVH itemVH, int i) {
        itemVH.bind(productBeans.get(i));
    }

    @Override
    public int getItemCount() {
        return productBeans.size();
    }

    public void setGroupList(List<Historyreportbean.Data> beritaBeanList1) {
        productBeans.clear();
        productBeans.addAll(beritaBeanList1);
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(beritaBeanList1);
        notifyDataSetChanged();
    }

    public void addLast(Historyreportbean.Data contact) {
        productBeans.add(contact);
        notifyDataSetChanged();
    }


    class ItemVH extends RecyclerView.ViewHolder {

        @BindView(R.id.titleVoucher)
        TextView titleVoucher;

        @BindView(R.id.titlePoint)
        TextView titlePoint;

        @BindView(R.id.titleDate)
        TextView titleDate;

        @BindView(R.id.titleStatus)
        TextView titleStatus;


        @BindView(R.id.btnDetail)
        LinearLayout btnDetail;

        ItemVH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Historyreportbean.Data beritaBean) {

            titleDate.setText(beritaBean.getCreated_at());

            if(beritaBean.getNo_polisi()!=null){
                titleVoucher.setText(beritaBean.getNo_polisi());
            }else{
                if(beritaBean.getNoCarPolice()!=null){
                    titleVoucher.setText(beritaBean.getNoCarPolice());
                }
            }


            if(beritaBean.getStatusHandling()==null){
                if(beritaBean.getStatus()==null){
                    titleStatus.setText("-");
                }else{
                    titleStatus.setText(beritaBean.getStatus());
                }
            }else{
                titleStatus.setText(beritaBean.getStatusHandling());
            }


            titlePoint.setText(beritaBean.getPoint()==null?"" : " +"+beritaBean.getPoint()+ " point");
            btnDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.OnClickBerita(beritaBean);
                }
            });

        }
    }


    public interface OnClickListener{
        void OnClickBerita(Historyreportbean.Data beritaBean);
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        productBeans.clear();
        if (charText.length() == 0) {
            productBeans.addAll(arraylist);
        } else {
            for (Historyreportbean.Data wp : arraylist) {
                if(wp.getNo_polisi()!=null){
                    if (wp.getNo_polisi().toLowerCase(Locale.getDefault()).contains(charText)) {
                        productBeans.add(wp);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

}
