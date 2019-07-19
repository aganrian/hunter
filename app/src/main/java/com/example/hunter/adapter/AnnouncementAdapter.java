package com.example.hunter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hunter.R;
import com.example.hunter.data.remote.bean.AnnouncementBean;
import com.example.hunter.data.remote.bean.HistoryRedeembean;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/*adapter untuk menghandle list dari announcement / berita*/
public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.ItemVH> {

    private List<AnnouncementBean.Data.Data2> productBeans;

    private Context context;

    @Inject
    public AnnouncementAdapter() {
        productBeans = new ArrayList<>();
    }

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public AnnouncementAdapter.ItemVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View root = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.announcement_item_layout, viewGroup, false);
        return new AnnouncementAdapter.ItemVH(root);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnouncementAdapter.ItemVH itemVH, int i) {
        itemVH.bind(productBeans.get(i));
    }

    @Override
    public int getItemCount() {
        return productBeans.size();
    }

    public void setGroupList(List<AnnouncementBean.Data.Data2> beritaBeanList1) {
        productBeans.clear();
        productBeans.addAll(beritaBeanList1);
        notifyDataSetChanged();
    }

    public void addLast(AnnouncementBean.Data.Data2 contact) {
        productBeans.add(contact);
        notifyDataSetChanged();
    }


    class ItemVH extends RecyclerView.ViewHolder {

        @BindView(R.id.contentAnnouncement)
        TextView contentAnnouncement;

        @BindView(R.id.titleAnnouncement)
        TextView titleAnnouncement;




        ItemVH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(AnnouncementBean.Data.Data2 beritaBean) {
            titleAnnouncement.setText(beritaBean.getTitle());
            contentAnnouncement.setText(beritaBean.getContent());
        }
    }


    public interface OnClickListener{
        void OnClickBerita(String title, String content, String time, String image);
    }

}
