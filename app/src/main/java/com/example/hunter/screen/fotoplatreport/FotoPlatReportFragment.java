package com.example.hunter.screen.fotoplatreport;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.hunter.base.BaseFragment;
import com.example.hunter.custom.CustomDialog;
import com.example.hunter.data.remote.bean.OcrBean;
import com.example.hunter.screen.main.MainActivity;
import com.example.hunter.screen.splash.SplashActivity;
import com.example.hunter.utils.GPSTracker;
import com.example.hunter.utils.ImageUtils;
import com.example.hunter.utils.constant.S;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.OnClick;
import id.oase.indonesia.oasebrdiepa.R;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class FotoPlatReportFragment extends BaseFragment implements FotoPlatReportContract.View {

    private static final int CAMERA_REQUEST_CODE = 100;

    @Inject
    FotoPlatReportContract.Presenter mPresenter;

    @Inject
    @Named("IMAGE_PATH")
    String imagePath;

    @Inject
    @Named("VEHICLE_ID")
    Integer vehicleId;

    @Inject
    @Named("NO_POLICE")
    String noPolice;


    @Inject
    @Named("MERK")
    String merk;

    @Inject
    @Named("TAHUN_KENDARAAN")
    Integer tahunKendaraan;

    @Inject
    @Named("JENIS_KENDARAAN")
    String jenisKendaraan;

    @Inject
    @Named("OVERDUE_MONTH")
    Integer overdueMonth;

    @Inject
    @Named("STATUS")
    String statusData;

    @Inject
    @Named("LEASING")
    String leasing;

    @Inject
    @Named("DEBITUR")
    String debiturName;

    @Inject
    @Named("STATUS_HANDLING")
    String statusHandling;

    @Inject
    @Named("ID_LAPOR")
    Integer idLapor;


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

    @BindView(R.id.textViewErroPenangann)
    TextView errorPenanganan;

    @BindView(R.id.txtStatus)
    TextView txtStatus;

    @BindView(R.id.lyPenanganan)
    LinearLayout lyPenanganan;

    @BindView(R.id.lyPenangananTidak)
    LinearLayout lyPenangananTidak;

    @BindView(R.id.rlUbahData)
    RelativeLayout rlUbahData;

    @BindView(R.id.rlMainUtama)
    ScrollView rlMainUtama;

    @BindView(R.id.edNoPolisiManual)
    EditText edNoPolisiManual;

    private GPSTracker gpsTracker;

    @Inject
    public FotoPlatReportFragment(){
        gpsTracker = new GPSTracker(parentActivity());
    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_fotoplat_report;
    }

    private File fileImage ;

    @Override
    protected void initView(Bundle state) {
        mPresenter.takeView(this);
        rlUbahData.setVisibility(View.GONE);
        rlMainUtama.setVisibility(View.VISIBLE);
        Bitmap bitmap = null;
        try {
            fileImage = new File(imagePath);
            bitmap = MediaStore.Images.Media.getBitmap(parentActivity().getContentResolver(), Uri.fromFile(fileImage));
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageView.setImageBitmap(ImageUtils.rotate(bitmap,0));
        System.gc();


        if(statusData.equalsIgnoreCase(S.FOUND_VEHICLE)){
            if(idLapor==0){
                if(statusHandling.equalsIgnoreCase("")){
                    txtStatus.setText(parentActivity().getString(R.string.foundData));
                    valueJenisKendaraan.setText(jenisKendaraan);
                    valueKendaraanLabel.setText(merk + " " + tahunKendaraan);
                    valueleasing.setText(leasing);
                    valueDebitur.setText(debiturName);
                    valueNomorPolisi.setText(noPolice);
                    valueStatus.setText(overdueMonth + " Months Overdue");
                    lyPenanganan.setVisibility(View.VISIBLE);
                    lyPenangananTidak.setVisibility(View.GONE);
                }else{
                    txtStatus.setText(parentActivity().getString(R.string.foundData));
                    valueJenisKendaraan.setText(jenisKendaraan);
                    valueKendaraanLabel.setText(merk + " " + tahunKendaraan);
                    valueleasing.setText(leasing);
                    valueDebitur.setText(debiturName);
                    valueNomorPolisi.setText(noPolice);
                    valueStatus.setText(overdueMonth + " Months Overdue");
                    lyPenanganan.setVisibility(View.GONE);
                    lyPenangananTidak.setVisibility(View.VISIBLE);
                    errorPenanganan.setVisibility(View.VISIBLE);
                    errorPenanganan.setText(getString(R.string.alreadyHandled));
                }
            }else{
                txtStatus.setText(parentActivity().getString(R.string.foundData));
                valueJenisKendaraan.setText(jenisKendaraan);
                valueKendaraanLabel.setText(merk + " " + tahunKendaraan);
                valueleasing.setText(leasing);
                valueDebitur.setText(debiturName);
                valueNomorPolisi.setText(noPolice);
                valueStatus.setText(overdueMonth + " Months Overdue");
                lyPenanganan.setVisibility(View.GONE);
                lyPenangananTidak.setVisibility(View.VISIBLE);
                errorPenanganan.setVisibility(View.VISIBLE);
                errorPenanganan.setText(getString(R.string.alreadyReport));
            }

        }else {
            txtStatus.setText(parentActivity().getString(R.string.notFoundData));
            valueJenisKendaraan.setText("-");
            valueKendaraanLabel.setText("-");
            valueleasing.setText("-");
            valueDebitur.setText("-");
            valueNomorPolisi.setText(noPolice);
            valueStatus.setText("-");
            lyPenanganan.setVisibility(View.GONE);
            lyPenangananTidak.setVisibility(View.VISIBLE);
            errorPenanganan.setVisibility(View.GONE);
        }

    }


    @Override
    public void setLoadingIndicator(boolean active) {
        progressBar.setVisibility(active ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showErrorMessage(String message) {
        CustomDialog customDialog = new CustomDialog();
        customDialog.showDialog(parentActivity(),"",message,"",
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
                gotoHistory();
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
        needPermissions(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION});
    }

    private String[] perms = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, parentActivity());
    }
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.e("OK", "Permission has been granted");
        if(gpsTracker.canGetLocation()){
            if(flag.equalsIgnoreCase(S.S_KIRIM_YA)){
                mPresenter.getVehicleReportWithHandler(vehicleId,noPolice,fileImage,String.valueOf(gpsTracker.getLatitude()),String.valueOf(gpsTracker.getLongitude()));
            }else if(flag.equalsIgnoreCase(S.S_KIRIM_NO)){
                mPresenter.getVehicleReport(vehicleId,noPolice,fileImage,String.valueOf(gpsTracker.getLatitude()),String.valueOf(gpsTracker.getLongitude()));
            }else{
                if(statusData.equalsIgnoreCase(S.NOT_FOUND_VEHICLE)){
                    mPresenter.getVehicleReport(noPolice,fileImage,String.valueOf(gpsTracker.getLatitude()),String.valueOf(gpsTracker.getLongitude()));
                }else{
                    mPresenter.getVehicleReport(vehicleId,noPolice,fileImage,String.valueOf(gpsTracker.getLatitude()),String.valueOf(gpsTracker.getLongitude()));
                }
            }
        }else{
            gpsTracker.showSettingsAlert();
        }
    }
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(parentActivity(), perms)) {
            new AppSettingsDialog.Builder(parentActivity()).build().show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
    }

    private String flag;

    @OnClick(R.id.btnKirim)
    public void onKirim(){
        flag = S.S_KIRIM_YA;
        gpsTracker = new GPSTracker(parentActivity());
        if(EasyPermissions.hasPermissions(parentActivity(), perms)){
            if(gpsTracker.canGetLocation()){
                mPresenter.getVehicleReportWithHandler(vehicleId,noPolice,fileImage,String.valueOf(gpsTracker.getLatitude()),String.valueOf(gpsTracker.getLongitude()));
            }else{
                gpsTracker.showSettingsAlert();
            }
        }else{
            EasyPermissions.requestPermissions(this, getString(R.string.message_permission), CAMERA_REQUEST_CODE, perms);
        }
    }


    @OnClick(R.id.btnBatal)
    public void btnBtl(){
        flag = S.S_KIRIM_NO;
        gpsTracker = new GPSTracker(parentActivity());
        if(EasyPermissions.hasPermissions(parentActivity(), perms)){
            if(gpsTracker.canGetLocation()){
                mPresenter.getVehicleReport(vehicleId,noPolice,fileImage,String.valueOf(gpsTracker.getLatitude()),String.valueOf(gpsTracker.getLongitude()));
            }else{
                gpsTracker.showSettingsAlert();
            }
        }else{
            EasyPermissions.requestPermissions(this, getString(R.string.message_permission), CAMERA_REQUEST_CODE, perms);
        }
    }

    @OnClick(R.id.btnOke)
    public void btnOke(){
        flag = S.S_KIRIM_OK;
        gpsTracker = new GPSTracker(parentActivity());
        if(EasyPermissions.hasPermissions(parentActivity(), perms)){
            if(gpsTracker.canGetLocation()){
                if(statusData.equalsIgnoreCase(S.NOT_FOUND_VEHICLE)){
                    mPresenter.getVehicleReport(noPolice,fileImage,String.valueOf(gpsTracker.getLatitude()),String.valueOf(gpsTracker.getLongitude()));
                }else{
                    mPresenter.getVehicleReport(vehicleId,noPolice,fileImage,String.valueOf(gpsTracker.getLatitude()),String.valueOf(gpsTracker.getLongitude()));
                }
            }else{
                gpsTracker.showSettingsAlert();
            }
        }else{
            EasyPermissions.requestPermissions(this, getString(R.string.message_permission), CAMERA_REQUEST_CODE, perms);
        }
    }

    @Override
    public void gotoHistory(){
        Intent i = new Intent(parentActivity(), MainActivity.class);
        i.putExtra(MainActivity.FROM,1);
        startActivity(i);
        parentActivity().finish();
    }

    @OnClick(R.id.ubahData)
    public void onClikcUbahData(){
        rlUbahData.setVisibility(View.VISIBLE);
        rlMainUtama.setVisibility(View.GONE);
    }

    @OnClick(R.id.btnBatalManual)
    public void onClickBatalManual(){
        Intent i = new Intent(parentActivity(), MainActivity.class);
        i.putExtra(MainActivity.FROM,0);
        startActivity(i);
        parentActivity().finish();
    }

    @Override
    public void gotoSummary(String status, OcrBean.Data data) {
        Intent intent = null;
        if(status.equalsIgnoreCase(S.FOUND_VEHICLE) ){

            if(data.getIdLapor()==null){
                if(data.getStatus_handling()==null){
                    intent = new Intent(parentActivity(), FotoPlatReportActivity.class);
                    intent.putExtra(FotoPlatReportActivity.STATUS, status);
                    intent.putExtra(FotoPlatReportActivity.IMAGE_PATH, imagePath);
                    intent.putExtra(FotoPlatReportActivity.NO_POLICE, data.getNoCarPolice());
                    intent.putExtra(FotoPlatReportActivity.VEHICLE_ID, data.getVehicleId());
                    intent.putExtra(FotoPlatReportActivity.MERK, data.getDescVehicleType());
                    intent.putExtra(FotoPlatReportActivity.TAHUN_KENDARAAN,data.getVehicleYear());
                    intent.putExtra(FotoPlatReportActivity.OVERDUE_MONTH, data.getOverdueMonth());
                    intent.putExtra(FotoPlatReportActivity.JENIS_KENDARAAN, data.getVehicleCategory());
                    intent.putExtra(FotoPlatReportActivity.DEBITUR, data.getCustomerName());
                    intent.putExtra(FotoPlatReportActivity.LEASING, data.getLeasingName());
                    intent.putExtra(FotoPlatReportActivity.STATUS_HANDLING,"");
                    intent.putExtra(FotoPlatReportActivity.ID_LAPOR,0);
                }else {
                    intent = new Intent(parentActivity(), FotoPlatReportActivity.class);
                    intent.putExtra(FotoPlatReportActivity.STATUS, status);
                    intent.putExtra(FotoPlatReportActivity.IMAGE_PATH, imagePath);
                    intent.putExtra(FotoPlatReportActivity.NO_POLICE, data.getNoCarPolice());
                    intent.putExtra(FotoPlatReportActivity.VEHICLE_ID, data.getVehicleId());
                    intent.putExtra(FotoPlatReportActivity.MERK, data.getDescVehicleType());
                    intent.putExtra(FotoPlatReportActivity.TAHUN_KENDARAAN,data.getVehicleYear());
                    intent.putExtra(FotoPlatReportActivity.OVERDUE_MONTH, data.getOverdueMonth());
                    intent.putExtra(FotoPlatReportActivity.JENIS_KENDARAAN, data.getVehicleCategory());
                    intent.putExtra(FotoPlatReportActivity.DEBITUR, data.getCustomerName());
                    intent.putExtra(FotoPlatReportActivity.LEASING, data.getLeasingName());
                    intent.putExtra(FotoPlatReportActivity.STATUS_HANDLING,data.getStatus_handling());
                    intent.putExtra(FotoPlatReportActivity.ID_LAPOR,0);
                }
            }else{
                intent = new Intent(parentActivity(), FotoPlatReportActivity.class);
                intent.putExtra(FotoPlatReportActivity.STATUS, status);
                intent.putExtra(FotoPlatReportActivity.IMAGE_PATH, imagePath);
                intent.putExtra(FotoPlatReportActivity.NO_POLICE, data.getNoCarPolice());
                intent.putExtra(FotoPlatReportActivity.VEHICLE_ID, data.getVehicleId());
                intent.putExtra(FotoPlatReportActivity.MERK, data.getDescVehicleType());
                intent.putExtra(FotoPlatReportActivity.TAHUN_KENDARAAN,data.getVehicleYear());
                intent.putExtra(FotoPlatReportActivity.OVERDUE_MONTH, data.getOverdueMonth());
                intent.putExtra(FotoPlatReportActivity.JENIS_KENDARAAN, data.getVehicleCategory());
                intent.putExtra(FotoPlatReportActivity.DEBITUR, data.getCustomerName());
                intent.putExtra(FotoPlatReportActivity.LEASING, data.getLeasingName());
                intent.putExtra(FotoPlatReportActivity.STATUS_HANDLING,"");
                intent.putExtra(FotoPlatReportActivity.ID_LAPOR,data.getIdLapor());
            }


        }else{
            intent = new Intent(parentActivity(), FotoPlatReportActivity.class);
            intent.putExtra(FotoPlatReportActivity.STATUS, status);
            intent.putExtra(FotoPlatReportActivity.IMAGE_PATH, imagePath);
            intent.putExtra(FotoPlatReportActivity.NO_POLICE, data.getNoCarPolice());
            intent.putExtra(FotoPlatReportActivity.VEHICLE_ID, 0);
            intent.putExtra(FotoPlatReportActivity.MERK, "");
            intent.putExtra(FotoPlatReportActivity.TAHUN_KENDARAAN,0);
            intent.putExtra(FotoPlatReportActivity.OVERDUE_MONTH, 0);
            intent.putExtra(FotoPlatReportActivity.JENIS_KENDARAAN, "");
            intent.putExtra(FotoPlatReportActivity.DEBITUR, "");
            intent.putExtra(FotoPlatReportActivity.LEASING, "");
            intent.putExtra(FotoPlatReportActivity.STATUS_HANDLING,"");
            intent.putExtra(FotoPlatReportActivity.ID_LAPOR,0);
        }

        startActivity(intent);
        parentActivity().finish();

    }

    @OnClick(R.id.btnKirimManual)
    public void btnKirimManual(){
        mPresenter.kirimManual(edNoPolisiManual.getText().toString());
    }

}
