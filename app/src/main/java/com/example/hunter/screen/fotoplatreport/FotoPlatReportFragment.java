package com.example.hunter.screen.fotoplatreport;

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

import com.example.hunter.base.BaseFragment;
import com.example.hunter.custom.CustomDialog;
import com.example.hunter.data.remote.bean.OcrBean;
import com.example.hunter.screen.main.MainActivity;
import com.example.hunter.screen.splash.SplashActivity;
import com.example.hunter.utils.ImageUtils;
import com.example.hunter.utils.constant.S;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.OnClick;
import id.oase.indonesia.oasebrdiepa.R;

public class FotoPlatReportFragment extends BaseFragment implements FotoPlatReportContract.View {

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

    @Inject
    public FotoPlatReportFragment(){

    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_fotoplat_report;
    }

    @Override
    protected void initView(Bundle state) {
        mPresenter.takeView(this);
        rlUbahData.setVisibility(View.GONE);
        rlMainUtama.setVisibility(View.VISIBLE);
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(parentActivity().getContentResolver(), Uri.fromFile(new File(imagePath)));
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
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
    }

    @OnClick(R.id.btnKirim)
    public void onKirim(){
        mPresenter.getVehicleReportWithHandler(vehicleId,noPolice);
    }


    @OnClick(R.id.btnBatal)
    public void btnBtl(){
        mPresenter.getVehicleReport(vehicleId,noPolice);
    }

    @OnClick(R.id.btnOke)
    public void btnOke(){
        if(statusData.equalsIgnoreCase(S.NOT_FOUND_VEHICLE)){
            mPresenter.getVehicleReport(noPolice);
        }else{
            mPresenter.getVehicleReport(vehicleId,noPolice);
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

    @Override
    public void showPenawaranMitra(Integer partnerId) {
        CustomDialog customDialog = new CustomDialog();
        customDialog.showDialog(parentActivity(),getString(R.string.titleMitraResmi),getString(R.string.contentMitraResmi),
                getString(R.string.footerMitraResmi),false,true,true);
        customDialog.setOnDialogResultListener(new CustomDialog.OnDialogClickBtnListener() {
            @Override
            public void onPositiveLisneter() {

            }

            @Override
            public void onNegativeListener() {
                    showDialog("Terima kasih sudah berpartisipasi mengirimkan\nlaporan kepada Kami.Anda akan mendapatkan\npoin untuk laporan ini");
            }

            @Override
            public void onOkListener() {

            }
        });
    }


    private void showDialog(String message){
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
}
