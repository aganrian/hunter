package com.example.hunter.screen.biodata;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.hunter.base.BaseFragment;
import com.example.hunter.custom.CustomDialog;
import com.example.hunter.screen.login.LoginContract;
import com.example.hunter.screen.main.MainActivity;
import com.example.hunter.screen.register.RegisterActivity;
import com.example.hunter.utils.ImageUtils;
import com.example.hunter.utils.KeyboardUtils;
import com.example.hunter.utils.constant.S;
import com.google.android.material.textfield.TextInputEditText;
import com.yalantis.ucrop.UCrop;

import net.igenius.customcheckbox.CustomCheckBox;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import com.example.hunter.R;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;

public class BiodataFragment extends BaseFragment implements BiodataContract.View {

    private static final int CAMERA_REQUEST_CODE_PROFILE = 100;
    private static final int CAMERA_REQUEST_CODE_KTP = 101;

    @Inject
    @Named("USER_ID_BIODATA")
    Integer idUser;

    @Inject
    @Named("USER_EMAIL_BIODATA")
    String userEmail;

    @Inject
    @Named("USER_NAMA_BIODATA")
    String nama;


    @Inject
    @Named("USER_OTP_HIDDEN")
    String otpHidden;

    @Inject
    BiodataContract.Presenter mPresenterLogin;

    @BindView(R.id.biodataNama)
    TextInputEditText biodataNama;

    @BindView(R.id.biodataEmail)
    TextInputEditText biodataEmail;

    @BindView(R.id.wanitaChoose)
    CustomCheckBox wanitaChoose;

    @BindView(R.id.lakiChoose)
    CustomCheckBox lakiChoose;

    @BindView(R.id.lyLakiLaki)
    LinearLayout lyLakiLaki;

    @BindView(R.id.lyPerempuan)
    LinearLayout lyPerempuanl;

    private RadioButton radioSexButton;

    @BindView(R.id.biodataTanggalLahir)
    EditText biodataTanggalLahir;

    @BindView(R.id.biodataKtp)
    TextInputEditText biodataKtp;

    @BindView(R.id.ly_loading_layout)
    RelativeLayout progressBar;

    File imageProfile , imageKtp;

    @BindView(R.id.ivProfile)
    CircleImageView ivProfile;


    @BindView(R.id.ivKtp)
    ImageView ivKtp;

    Integer choose = 0;

    private String valueJenisGender;

    @Inject
    public BiodataFragment(){

    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_biodata;
    }

    @Override
    protected void initView(Bundle state) {
        mPresenterLogin.takeView(this);
        lakiChoose.setChecked(true);
        wanitaChoose.setChecked(false);
        biodataNama.setEnabled(false);
        biodataEmail.setEnabled(false);
        biodataEmail.setText(userEmail);
        biodataNama.setText(nama);
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

            }
        });
    }

    @Override
    public void gotoHomeActivity() {
        Intent intent =  new Intent(parentActivity(), MainActivity.class);
        startActivity(intent);
        parentActivity().finishAffinity();
    }

    /*saat tombol biodata mengirimkan data ke backend*/
    @OnClick(R.id.nextButton)
    public void doLogin(){
        if(wanitaChoose.isChecked()){
            valueJenisGender = S.PEREMPUAN;
        }else if(lakiChoose.isChecked()){
            valueJenisGender = S.LAKI_LAKI;
        }
        mPresenterLogin.doNext(idUser,otpHidden,biodataNama.getText().toString(),
                biodataTanggalLahir.getText().toString(),valueJenisGender,biodataEmail.getText().toString(),
                biodataKtp.getText().toString(),imageProfile,imageKtp);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenterLogin.takeView(this);
        KeyboardUtils.hide(biodataTanggalLahir);
        needPermissions(new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE});
    }

    @OnClick(R.id.lyPerempuan)
    public void izinChoose(){
        wanitaChoose.setChecked(true);
        lakiChoose.setChecked(false);

    }

    @OnClick(R.id.wanitaChoose)
    public void absenChoose(){
        lakiChoose.setChecked(false);
        wanitaChoose.setChecked(true);

    }

    @OnClick(R.id.lyLakiLaki)
    public void dinasChooseBullet(){
        lakiChoose.setChecked(true);
        wanitaChoose.setChecked(false);

    }

    @OnClick(R.id.lakiChoose)
    public void absenChooseBullet(){
        wanitaChoose.setChecked(false);
        lakiChoose.setChecked(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenterLogin.dropView();
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

            EasyImage.openCamera(this, requestCode);
    }
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(parentActivity(), perms)) {
            new AppSettingsDialog.Builder(parentActivity()).build().show();
        }
    }

    @OnClick(R.id.ivProfile)
    public void onClickIvProfile(){
        choose = CAMERA_REQUEST_CODE_PROFILE;
        if(EasyPermissions.hasPermissions(parentActivity(), perms)){
            EasyImage.openCamera(this, 0);
        }else{
            EasyPermissions.requestPermissions(this, getString(R.string.message_permission), CAMERA_REQUEST_CODE_PROFILE, perms);
        }
    }

    @OnClick(R.id.ivKtp)
    public void onClickIvKtp(){
        choose = CAMERA_REQUEST_CODE_KTP;
        if(EasyPermissions.hasPermissions(parentActivity(), perms)){
            EasyImage.openCamera(this, 0);
        }else{
            EasyPermissions.requestPermissions(this, getString(R.string.message_permission), CAMERA_REQUEST_CODE_KTP, perms);
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
                    if(choose == CAMERA_REQUEST_CODE_PROFILE){
                        imageProfile = imageFile;
                        ImageUtils.displayImageRoundFromFile(parentActivity(), ivProfile, imageFile,null);
                    }else{
                        Uri selectedUri = Uri.fromFile(imageFile);
                        startCrop(selectedUri);
                        ImageUtils.displayImageFromFile(parentActivity(), ivKtp, imageFile,null);
                    }

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
                imageKtp = new File(resultUri.getPath());
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(parentActivity().getContentResolver(), resultUri);
                ivKtp.setImageBitmap(ImageUtils.rotate(bitmap,0));
                System.gc();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //uriToBitmap(resultUri);
        } else {
            Toast.makeText(parentActivity(), R.string.toast_unexpected_error, Toast.LENGTH_SHORT).show();
        }
    }

    private static final String SAMPLE_CROPPED_IMAGE_NAME = "CropImaged_Ktp";
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

    @OnClick(R.id.biodataTanggalLahir)
    public void onClickTanggalLahir(View view){
        KeyboardUtils.hide(view);
        Calendar c = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(parentActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String _year = String.valueOf(year);
                String _month = (month+1) < 10 ? "0" + (month+1) : String.valueOf(month+1);
                String _date = dayOfMonth < 10 ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
                String _pickedDate = year + "-" + _month + "-" + _date;
                biodataTanggalLahir.setText(_pickedDate);

            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMaxDate(new Date().getTime());
        dialog.show();
    }

}
