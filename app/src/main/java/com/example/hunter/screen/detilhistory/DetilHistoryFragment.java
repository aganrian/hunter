package com.example.hunter.screen.detilhistory;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.hunter.base.BaseFragment;
import com.example.hunter.custom.CustomDialog;
import com.example.hunter.data.remote.bean.Historyreportbean;
import com.example.hunter.screen.fotoplatreport.FotoPlatReportContract;
import com.example.hunter.screen.main.MainActivity;
import com.example.hunter.utils.ImageUtils;
import com.example.hunter.utils.TimeUtils;
import com.example.hunter.utils.constant.S;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.OnClick;
import id.oase.indonesia.oasebrdiepa.BuildConfig;
import id.oase.indonesia.oasebrdiepa.R;
import pl.aprilapps.easyphotopicker.EasyImage;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class DetilHistoryFragment extends BaseFragment implements DetilHistoryContract.View {

    @Inject
    DetilHistoryContract.Presenter mPresenter;

    @Inject
    @Named("DATA")
    Historyreportbean.Data data;


    @BindView(R.id.imageplat)
    ImageView imageView;

    @BindView(R.id.ly_loading_layout)
    RelativeLayout progressBar;

    @BindView(R.id.valueNomorPolisi)
    TextView valueNomorPolisi;

    @BindView(R.id.valueDebitur)
    TextView valueDebitur;

    @BindView(R.id.valueJenisKendaraan)
    TextView valueJenisKendaraan;

    @BindView(R.id.valueKendaraanLabel)
    TextView valueKendaraanLabel;

    @BindView(R.id.valueleasing)
    TextView valueleasing;


    @BindView(R.id.valueStatus)
    TextView valueStatus;

    @BindView(R.id.txtStatus)
    TextView txtStatus;

    @BindView(R.id.tglLaporanValue)
    TextView tglLaporan;

    @BindView(R.id.valueStatusLaporan)
    TextView statusLaporan;

    @BindView(R.id.btnAjuan)
    LinearLayout btnAjuan;

    @BindView(R.id.txtButtonAjuan)
    TextView txtButtnAjuan;

    @Inject
    public DetilHistoryFragment(){

    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_detilhistory;
    }

    @Override
    protected void initView(Bundle state) {
        mPresenter.takeView(this);
        ImageUtils.displayImageFromUrl(parentActivity(),imageView,
                BuildConfig.API_URL_IMAGE_PLAT.concat(data.getPict_tumbnail()),null,R.drawable.ic_person);


        if(data.getNo_polisi()!=null){
            valueNomorPolisi.setText(data.getNo_polisi());
        }else{
            if(data.getNoCarPolice()!=null){
                valueNomorPolisi.setText(data.getNoCarPolice());
            }
        }


        if(data.getCustomerName()==null || data.getCustomerName().equalsIgnoreCase("")){
            valueDebitur.setText("-");
        }else{
            valueDebitur.setText(data.getCustomerName());
        }

        if(data.getDescVehicleType()==null || data.getDescVehicleType().equalsIgnoreCase("")){
            valueJenisKendaraan.setText("-");
            valueKendaraanLabel.setText("-");
        }else{
            valueJenisKendaraan.setText(getString(R.string.mobil));
            valueKendaraanLabel.setText(data.getDescVehicleType() + " " +data.getVehicleYear());
        }


        if(data.getPartnerName()==null || data.getPartnerName().equalsIgnoreCase("")){
            valueleasing.setText("-");
        }else{
            valueleasing.setText(data.getPartnerName());
        }

        if(data.getOverdueMonth()==null){
            valueStatus.setText("-");
        }else{
            valueStatus.setText(data.getOverdueMonth() +" "+ getString(R.string.monthOverdue));
        }

        if(data.getStatus()==null){
            txtStatus.setText("-");
        }else{
            txtStatus.setText(data.getStatus());
        }


        if(data.getStatusHandling()==null){
            if(data.getStatus()==null){
                statusLaporan.setText("-");
            }else{
                statusLaporan.setText(data.getStatus());
            }
        }else{
            String s = "";
            if(data.getExpiredAt()==null){
                s = " (" + data.getExpiredAt()+ ")";
            }
            statusLaporan.setText(data.getStatusHandling() + s );
        }


        if(data.getCreated_at()==null){
            tglLaporan.setText("-");
        }else{
            tglLaporan.setText(TimeUtils.getDateUlasan(data.getCreated_at()));
        }

        if(data.getStatusHandling()!=null){
            btnAjuan.setVisibility(View.VISIBLE);
            if(data.getStatusHandling().equalsIgnoreCase(S.PROSES_PENANGANAN) || data.getStatusHandling().equalsIgnoreCase(S.BERHASIL_DITANGANI)){
                txtButtnAjuan.setText(getString(R.string.suratPenunjukan));
            }else{
                txtButtnAjuan.setText(getString(R.string.ajukanPerpanjangan));
            }
        }else{
            btnAjuan.setVisibility(View.GONE);
        }



    }

    private static final int CAMERA_REQUEST_CODE = 100;
    @OnClick(R.id.btnAjuan)
    public void btnAjuan(){
        if(data.getStatusHandling()!=null){
            if(data.getStatusHandling().equalsIgnoreCase(S.PROSES_PENANGANAN) || data.getStatusHandling().equalsIgnoreCase(S.BERHASIL_DITANGANI)){
                if(EasyPermissions.hasPermissions(parentActivity(), perms)){
                    downloadManager(BuildConfig.API_URL+"vehicles/sk?vehicle_id="+data.getId_btmk());
                }else{
                    EasyPermissions.requestPermissions(this, getString(R.string.message_permission), CAMERA_REQUEST_CODE, perms);
                }
            }else{
                mPresenter.ajukanPerpanjangan(data.getId_btmk());
            }
        }
    }

    @Override
    public void showDialogSuccess() {
        CustomDialog customDialog = new CustomDialog();
        customDialog.showDialog(parentActivity(),"",getString(R.string.success_extend),"",
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
                Intent i = new Intent(parentActivity(), MainActivity.class);
                i.putExtra(MainActivity.FROM,1);
                startActivity(i);
                parentActivity().finish();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, parentActivity());
    }
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.e("OK", "Permission has been granted");
        downloadManager(BuildConfig.API_URL+"vehicles/sk?vehicle_id="+data.getId_btmk());
    }
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(parentActivity(), perms)) {
            new AppSettingsDialog.Builder(parentActivity()).build().show();
        }
    }

    private void downloadManager(String url){
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDescription("Some descrition");
        request.setTitle("Some title");
// in order for this if to run, you must use the android 3.2 to compile your app
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "hunter/pdf");

// get download service and enqueue file
        DownloadManager manager = (DownloadManager) parentActivity().getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }



    @OnClick(R.id.ibBack)
    public void back(){
        Intent i = new Intent(parentActivity(), MainActivity.class);
        i.putExtra(MainActivity.FROM,1);
        startActivity(i);
        parentActivity().finish();
    }


    @Override
    public void setLoadingIndicator(boolean active) {
        progressBar.setVisibility(active ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
        needPermissions(new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE});
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
    }

    private String[] perms = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

}
