package com.example.hunter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.hunter.data.remote.bean.AnnouncementBean;
import com.example.hunter.data.remote.bean.ProvinceBean;
import com.example.hunter.utils.constant.S;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.oase.indonesia.oasebrdiepa.R;

public class ListCustomAdapter extends RecyclerView.Adapter<ListCustomAdapter.ItemVH> {

    private Context mContext;
    private List<ProvinceBean.Data> provinceBeanList;
    public int selectedPosition = -1;
    private OnItemClickListener mItemClickListener;
    private String type = "";


    public ListCustomAdapter(Context context, List<ProvinceBean.Data> provinceBeanList,String type) {
        this.provinceBeanList = provinceBeanList;
        this.mContext = context;
        this.type = type;
    }

    public void setmItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public ListCustomAdapter.ItemVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_custom, parent, false);
        return new ListCustomAdapter.ItemVH(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ListCustomAdapter.ItemVH holder, final int position) {
        String mAccountModel = "";
        if(type.equalsIgnoreCase(S.PROVINCE)){
            mAccountModel = provinceBeanList.get(position).getProvince();
        }else if(type.equalsIgnoreCase(S.REGENCY)){
            mAccountModel = provinceBeanList.get(position).getRegency();
        }else if(type.equalsIgnoreCase(S.DISTRICT)){
            mAccountModel = provinceBeanList.get(position).getDistrict();
        }else if(type.equalsIgnoreCase(S.VILLAGE)){
            mAccountModel = provinceBeanList.get(position).getVillage();
        }else if(type.equalsIgnoreCase(S.POSTCODE)){
            mAccountModel = provinceBeanList.get(position).getPostcode();
        }



        holder.itemProvinsi.setText(mAccountModel);
        if(selectedPosition!=-1 && selectedPosition == position) {
            holder.itemList.setSelected(true);
            holder.itemList.setBackgroundColor(holder.view.getResources().getColor(R.color.textColorSelected));
        } else {
            holder.itemList.setSelected(false);
            holder.itemList.setBackgroundColor(holder.view.getResources().getColor(android.R.color.transparent));
        }

        holder.itemList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectedPosition >= 0)
                    notifyItemChanged(selectedPosition);
                selectedPosition = holder.getAdapterPosition();
                notifyItemChanged(selectedPosition);

                if (mItemClickListener != null) {
                    if(type.equalsIgnoreCase(S.PROVINCE)){
                        mItemClickListener.onItemClick(provinceBeanList.get(position).getProvince(),
                                provinceBeanList.get(position).getId());
                    }else if(type.equalsIgnoreCase(S.REGENCY)){
                        mItemClickListener.onItemClick(provinceBeanList.get(position).getRegency(),
                                provinceBeanList.get(position).getId());
                    }else if(type.equalsIgnoreCase(S.DISTRICT)){
                        mItemClickListener.onItemClick(provinceBeanList.get(position).getDistrict(),
                                provinceBeanList.get(position).getId());
                    }else if(type.equalsIgnoreCase(S.VILLAGE)){
                        mItemClickListener.onItemClick(provinceBeanList.get(position).getVillage(),
                                provinceBeanList.get(position).getId());
                    }else if(type.equalsIgnoreCase(S.POSTCODE)){
                        mItemClickListener.onItemClick(provinceBeanList.get(position).getPostcode(),
                                provinceBeanList.get(position).getId());
                    }
                }

                selectedPosition = holder.getAdapterPosition();

            }
        });
    }

    @Override
    public int getItemCount() {
        return provinceBeanList.size();
    }

    public class ItemVH extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_item_norek)
        TextView itemProvinsi;

        @BindView(R.id.item_list)
        RelativeLayout itemList;

        View view;
        public ItemVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            view = itemView;
        }
    }


    public interface OnItemClickListener {
        public void onItemClick(String province,Integer idProvince);

    }
}
