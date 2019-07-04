package com.example.hunter.screen.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hunter.adapter.ProductAdapter;
import com.example.hunter.base.BaseFragment;
import com.example.hunter.custom.CustomDialog;
import com.example.hunter.data.remote.bean.ProductBean;
import com.example.hunter.screen.main.MainActivity;
import com.example.hunter.utils.ImageUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import id.oase.indonesia.oasebrdiepa.BuildConfig;
import id.oase.indonesia.oasebrdiepa.R;

public class HomeFragment extends BaseFragment implements HomeContract.View,ProductAdapter.OnClickListener {

    @Inject
    HomeContract.Presenter mPresenterLogin;

    @BindView(R.id.ivImage)
    CircleImageView circleImageView;

    @BindView(R.id.tvPoint)
    TextView tvPoint;

    @BindView(R.id.nameKaryawan)
    TextView nameKaryawan;

    @BindView(R.id.ly_loading_layout)
    RelativeLayout progressBar;

    @BindView(R.id.productRecycleview)
    RecyclerView productRecycleView;

    private Integer pointUser;

    @Inject
    ProductAdapter productAdapter;

    @Inject
    public HomeFragment(){

    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(Bundle state) {
        mPresenterLogin.takeView(this);
        mPresenterLogin.getHomeInformation();
        mPresenterLogin.getProductAll();
        productRecycleView.setLayoutManager(new GridLayoutManager(parentActivity(),2));
        productRecycleView.setHasFixedSize(true);
        productRecycleView.setAdapter(productAdapter);
        productAdapter.setOnClickListener(this);
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        parentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(active ? View.VISIBLE : View.GONE);
            }
        });

    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setHomeInformation(String urlPicture, String nama, Integer point) {
        parentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ImageUtils.displayImageFromUrl(parentActivity(),circleImageView,
                        BuildConfig.API_URL_IMAGE_PROFILE.concat(urlPicture),null,R.drawable.ic_person);
            }
        });
        setLoadingIndicator(false);
        pointUser = point;
        tvPoint.setText(String.valueOf(point)+" "+ getString(R.string.pointLabel));
        nameKaryawan.setText(nama);

    }

    @Override
    public void updatePoint(String point) {


        Intent i = new Intent(parentActivity(), MainActivity.class);
        i.putExtra(MainActivity.FROM,1);
        i.putExtra(MainActivity.SUBFROM,1);
        startActivity(i);
        parentActivity().finish();

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
    public void setListProduct(List<ProductBean.Data> data) {
        productAdapter.setGroupList(data);
    }

    @Override
    public void OnClickBerita(Integer productId,Integer price) {

        if(pointUser<price){
            CustomDialog customDialog = new CustomDialog();
            customDialog.showDialog(parentActivity(),"",getString(R.string.not_point_enough),"",
                    false,false,false);
            customDialog.setOnDialogResultListener(new CustomDialog.OnDialogClickBtnListener() {
                @Override
                public void onPositiveLisneter() {

                }

                @Override
                public void onNegativeListener() {

                }

                @Override
                public void onOkListener() {

                }
            });
        }else{
            CustomDialog customDialog = new CustomDialog();
            customDialog.showDialog(parentActivity(),"","",getString(R.string.wantRedeem),
                    true,false,false);
            customDialog.setOnDialogResultListener(new CustomDialog.OnDialogClickBtnListener() {
                @Override
                public void onPositiveLisneter() {
                    mPresenterLogin.productReadem(productId);
                }

                @Override
                public void onNegativeListener() {

                }

                @Override
                public void onOkListener() {

                }
            });
        }


    }
}
