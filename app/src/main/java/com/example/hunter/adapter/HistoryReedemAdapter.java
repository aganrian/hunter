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

public class HistoryReedemAdapter extends RecyclerView.Adapter<HistoryReedemAdapter.ItemVH> {

    private List<HistoryRedeembean.Data> productBeans;
    private ArrayList<HistoryRedeembean.Data> arraylist;
    private Context context;

    @Inject
    public HistoryReedemAdapter() {
        productBeans = new ArrayList<>();
    }

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public HistoryReedemAdapter.ItemVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View root = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.redeem_history_item_layout, viewGroup, false);
        return new HistoryReedemAdapter.ItemVH(root);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryReedemAdapter.ItemVH itemVH, int i) {
        itemVH.bind(productBeans.get(i));
    }

    @Override
    public int getItemCount() {
        return productBeans.size();
    }

    public void setGroupList(List<HistoryRedeembean.Data> beritaBeanList1) {
        productBeans.clear();
        productBeans.addAll(beritaBeanList1);
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(beritaBeanList1);
        notifyDataSetChanged();
    }

    public void addLast(HistoryRedeembean.Data contact) {
        productBeans.add(contact);
        notifyDataSetChanged();
    }


    class ItemVH extends RecyclerView.ViewHolder {

        @BindView(R.id.titleProduct)
        TextView titleProduct;

        @BindView(R.id.titlePoint)
        TextView titlePoint;

        @BindView(R.id.titleDate)
        TextView titleDate;

        @BindView(R.id.titleStatus)
        TextView titleStatus;

        @BindView(R.id.titleResi)
        TextView titleResi;



        ItemVH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(HistoryRedeembean.Data beritaBean) {

            titleDate.setText(beritaBean.getCreated_at());
            titleProduct.setText(beritaBean.getName());
            titleStatus.setText(beritaBean.getStatus());
            titlePoint.setText(" +"+beritaBean.getPoint()+" poin");
            titleResi.setText(beritaBean.getNoresi()==null?"" :" "+beritaBean.getNoresi());
        }
    }


    public interface OnClickListener{
        void OnClickBerita(String title, String content, String time, String image);
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        productBeans.clear();
        if (charText.length() == 0) {
            productBeans.addAll(arraylist);
        } else {
            for (HistoryRedeembean.Data wp : arraylist) {
                if(wp.getName()!=null){
                    if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                        productBeans.add(wp);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

}
