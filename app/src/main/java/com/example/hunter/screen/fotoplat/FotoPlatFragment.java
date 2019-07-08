package com.example.hunter.screen.fotoplat;

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
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.hunter.base.BaseFragment;
import com.example.hunter.data.remote.bean.OcrBean;
import com.example.hunter.screen.fotoplatreport.FotoPlatReportActivity;
import com.example.hunter.screen.main.MainActivity;
import com.example.hunter.utils.ImageUtils;
import com.example.hunter.utils.constant.S;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import id.oase.indonesia.oasebrdiepa.R;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;

public class FotoPlatFragment extends BaseFragment implements FotoPlatContract.View {

    private static final int CAMERA_REQUEST_CODE = 100;

    @Inject
    FotoPlatContract.Presenter mPresenterLogin;

    @BindView(R.id.ly_loading_layout)
    RelativeLayout progressBar;

    @BindView(R.id.imageplat)
    ImageView imageplat;

    @BindView(R.id.rlGagal)
    RelativeLayout relativeLayoutGagall;

    @BindView(R.id.lyButtonManual)
    RelativeLayout relativeLayoutButtonManual;

    @BindView(R.id.lyButtonOcr)
    RelativeLayout relativeLayoutButtonOcr;

    @BindView(R.id.edNoPolisiManual)
    EditText edNoPolisiManual;

    File file;

    @Inject
    public FotoPlatFragment(){

    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_fotoplat;
    }

    @Override
    protected void initView(Bundle state) {
        mPresenterLogin.takeView(this);
        relativeLayoutButtonOcr.setVisibility(View.VISIBLE);
        relativeLayoutButtonManual.setVisibility(View.GONE);
        relativeLayoutGagall.setVisibility(View.GONE);
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
    public void gotoSummary(String status, OcrBean.Data data) {
        Intent intent = null;
        if(status.equalsIgnoreCase(S.FOUND_VEHICLE) ){
            if(data.getIdLapor()==null){
                if(data.getStatus_handling()==null){
                    intent = new Intent(parentActivity(), FotoPlatReportActivity.class);
                    intent.putExtra(FotoPlatReportActivity.STATUS, status);
                    intent.putExtra(FotoPlatReportActivity.IMAGE_PATH, file.getAbsolutePath());
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
                    intent.putExtra(FotoPlatReportActivity.IMAGE_PATH, file.getAbsolutePath());
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
                intent.putExtra(FotoPlatReportActivity.IMAGE_PATH, file.getAbsolutePath());
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
            intent.putExtra(FotoPlatReportActivity.IMAGE_PATH, file.getAbsolutePath());
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

    }

    @Override
    public void showInputManual() {
        relativeLayoutGagall.setVisibility(View.VISIBLE);
        relativeLayoutButtonOcr.setVisibility(View.GONE);
        relativeLayoutButtonManual.setVisibility(View.VISIBLE);
    }


    @Override
    public void onResume() {
        super.onResume();
        mPresenterLogin.takeView(this);
        relativeLayoutButtonOcr.setVisibility(View.VISIBLE);
        relativeLayoutButtonManual.setVisibility(View.GONE);
        relativeLayoutGagall.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenterLogin.dropView();
    }

    @OnClick(R.id.btnKirim)
    public void kirim(){
        mPresenterLogin.kirimOcr(file);
    }

    @OnClick(R.id.btnKirimManual)
    public void onKirimManual(){
        mPresenterLogin.kirimManual(edNoPolisiManual.getText().toString());
    }


    @OnClick(R.id.btnBatalManual)
    public void batalManual(){
        Intent i = new Intent(parentActivity(), MainActivity.class);
        i.putExtra(MainActivity.FROM,0);
        startActivity(i);
        parentActivity().finish();
    }

    @OnClick(R.id.btnBatal)
    public void batalOcr(){
        Intent i = new Intent(parentActivity(), MainActivity.class);
        i.putExtra(MainActivity.FROM,0);
        startActivity(i);
        parentActivity().finish();
    }

    private String[] perms = {
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, parentActivity());
    }
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        EasyImage.openCamera(this, 0);
    }
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(parentActivity(), perms)) {
            new AppSettingsDialog.Builder(parentActivity()).build().show();
        }
    }

    @OnClick(R.id.lyImagePlat)
    public void onClickImage(){
        if(EasyPermissions.hasPermissions(parentActivity(), perms)){
            EasyImage.openCamera(this, 0);
        }else{
            EasyPermissions.requestPermissions(this, getString(R.string.message_permission), CAMERA_REQUEST_CODE, perms);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == UCrop.REQUEST_CROP) {
                handleCropResult(data);
            }
        }
        if (resultCode == UCrop.RESULT_ERROR) {
            handleCropError(data);
        }


        EasyImage.handleActivityResult(requestCode, resultCode, data, parentActivity(), new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                super.onImagePickerError(e, source, type);
                e.printStackTrace();

            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                if (imageFile != null) {
                    Uri selectedUri = Uri.fromFile(imageFile);
                    startCrop(selectedUri);
                }
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                super.onCanceled(source, type);
                if (source == EasyImage.ImageSource.CAMERA) {
                    File outputFile = EasyImage.lastlyTakenButCanceledPhoto(parentActivity());
                    if (outputFile != null) outputFile.delete();
                }
            }
        });
    }

    private void handleCropResult(@NonNull Intent result) {
        final Uri resultUri = UCrop.getOutput(result);
        if (resultUri != null) {
            //photoEditorView.getSource().setImageBitmap(r.getBitmap());
            try {
                file = new File(resultUri.getPath());
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(parentActivity().getContentResolver(), resultUri);
                imageplat.setImageBitmap(ImageUtils.rotate(bitmap,0));
                System.gc();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //uriToBitmap(resultUri);
        } else {
            Toast.makeText(parentActivity(), R.string.toast_unexpected_error, Toast.LENGTH_SHORT).show();
        }
    }

    private static final String SAMPLE_CROPPED_IMAGE_NAME = "CropImaged";
    private void startCrop(@NonNull Uri uri) {

        String destinationFileName = SAMPLE_CROPPED_IMAGE_NAME;
        destinationFileName += ".png";
        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(parentActivity().getCacheDir(), destinationFileName)));
        uCrop = advancedConfig(uCrop);
        uCrop.start(parentActivity(),this,UCrop.REQUEST_CROP);
    }

    private UCrop advancedConfig(@NonNull UCrop uCrop) {
        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.PNG);
        options.setCompressionQuality(90);
        options.setFreeStyleCropEnabled(true);
        return uCrop.withOptions(options);
    }

    private void handleCropError(@NonNull Intent result) {
        final Throwable cropError = UCrop.getError(result);
        if (cropError != null) {
            Toast.makeText(parentActivity(), cropError.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(parentActivity(), R.string.toast_unexpected_error, Toast.LENGTH_SHORT).show();
        }
    }

}
