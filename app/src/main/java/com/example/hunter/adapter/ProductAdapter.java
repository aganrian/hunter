package com.example.hunter.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hunter.data.remote.bean.ProductBean;
import com.example.hunter.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.hunter.BuildConfig;
import com.example.hunter.R;
/*Adapter untuk menghandle list dari product*/
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ItemVH> {

    private List<ProductBean.Data> productBeans;

    private Context context;

    @Inject
    public ProductAdapter(Context context) {
        this.context = context;
        productBeans = new ArrayList<>();
    }

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ProductAdapter.ItemVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View root = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_item_layout, viewGroup, false);
        return new ProductAdapter.ItemVH(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ItemVH itemVH, int i) {
        itemVH.bind(productBeans.get(i));
    }

    @Override
    public int getItemCount() {
        return productBeans.size();
    }

    public void setGroupList(List<ProductBean.Data> beritaBeanList1) {
        productBeans.clear();
        productBeans.addAll(beritaBeanList1);
        notifyDataSetChanged();
    }

    public void addLast(ProductBean.Data contact) {
        productBeans.add(contact);
        notifyDataSetChanged();
    }


    class ItemVH extends RecyclerView.ViewHolder {

        @BindView(R.id.titleVoucher)
        TextView titleVoucher;

        @BindView(R.id.titlePoint)
        TextView titlePoint;

        @BindView(R.id.imageProduk)
        ImageView imageProduk;

        @BindView(R.id.btnReddem)
        LinearLayout btnReedem;

        ItemVH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(ProductBean.Data beritaBean) {

            titlePoint.setText(String.valueOf(beritaBean.getPrice()) + " point");
            titleVoucher.setText(beritaBean.getName());
            ImageUtils.displayImageFromUrl(context,imageProduk,
                    BuildConfig.API_URL_IMAGE_PRODUCT.concat(beritaBean.getImage()),null,R.drawable.ic_person);


            btnReedem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onClickListener!=null){
                        onClickListener.OnClickBerita(beritaBean.getIdProduct(),beritaBean.getPrice());
                    }
                }
            });


        }
    }


    public interface OnClickListener{
        void OnClickBerita(Integer productId,Integer point);
    }

}
