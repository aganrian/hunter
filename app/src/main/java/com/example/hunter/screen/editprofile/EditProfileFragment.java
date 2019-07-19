package com.example.hunter.screen.editprofile;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.hunter.base.BaseFragment;
import com.example.hunter.custom.CustomDialog;
import com.example.hunter.data.local.db.entity.UserEntity;
import com.example.hunter.screen.main.MainActivity;
import com.example.hunter.utils.ImageUtils;
import com.example.hunter.utils.KeyboardUtils;
import com.example.hunter.utils.constant.S;
import com.yalantis.ucrop.UCrop;

import net.igenius.customcheckbox.CustomCheckBox;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import com.example.hunter.BuildConfig;
import com.example.hunter.R;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;

public class EditProfileFragment extends BaseFragment implements EditProfileContract.View {

    private static final int CAMERA_REQUEST_CODE_PROFILE = 100;
    private static final int CAMERA_REQUEST_CODE_KTP = 101;

    @Inject
    EditProfileContract.Presenter mPresenterLogin;

    @BindView(R.id.ly_loading_layout)
    RelativeLayout progressBar;

    @BindView(R.id.ivImage)
    ImageView imageProfileView;

    @BindView(R.id.ivKtp)
    ImageView ivKtp;

    @BindView(R.id.namaEdittext)
    EditText edNama;

    @BindView(R.id.emailEdittext)
    EditText edEmail;

    @BindView(R.id.ktpEdittext)
    EditText edNoKtp;

    @BindView(R.id.tglLahirEdittext)
    EditText edTglLahir;

    @BindView(R.id.noTelpEdittext)
    EditText edNoTelp;

    @BindView(R.id.wanitaChoose)
    CustomCheckBox wanitaChoose;

    @BindView(R.id.lakiChoose)
    CustomCheckBox lakiChoose;

    @BindView(R.id.lyLakiLaki)
    LinearLayout lyLakiLaki;

    @BindView(R.id.lyPerempuan)
    LinearLayout lyPerempuanl;

    File imageProfile , imageKtp;


    @Inject
    public EditProfileFragment(){

    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_edit_profile;
    }

    @Override
    protected void initView(Bundle state) {
        mPresenterLogin.takeView(this);
        /*get data profile dari sql lite*/
        mPresenterLogin.getDataProfile();
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
    public void onResume() {
        super.onResume();
        mPresenterLogin.takeView(this);
        needPermissions(new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE});

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenterLogin.dropView();
    }

    /*set data profile dari presenter*/
    @Override
    public void setDataProfile(UserEntity userEntity) {

        edNama.setText(userEntity.getNama_lengkap());
        edEmail.setText(userEntity.getEmail());
        edNoTelp.setText(userEntity.getNo_hp());
        edNoKtp.setText(userEntity.getNo_ktp());
        edTglLahir.setText(userEntity.getTgl_lahir());

        parentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ImageUtils.displayImageFromUrl(parentActivity(), imageProfileView,
                        BuildConfig.API_URL_IMAGE_PROFILE.concat(userEntity.getPicture()==null?"":userEntity.getPicture()),null,R.drawable.ic_person);
            }
        });

        parentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ImageUtils.displayImageFromUrl(parentActivity(),ivKtp,
                        BuildConfig.API_URL_IMAGE_PROFILE.concat(userEntity.getKtp_picture()==null?"":userEntity.getKtp_picture()),null,R.drawable.ic_person);
            }
        });


        if(userEntity.getGender()==null){
            wanitaChoose.setChecked(false);
            lakiChoose.setChecked(true);
        }else{
            if(userEntity.getGender().equalsIgnoreCase(S.LAKI_LAKI)){
                wanitaChoose.setChecked(false);
                lakiChoose.setChecked(true);
            }else {
                wanitaChoose.setChecked(true);
                lakiChoose.setChecked(false);
            }
        }


    }

    @OnClick(R.id.tglLahirEdittext)
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
                edTglLahir.setText(_pickedDate);

            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMaxDate(new Date().getTime());
        dialog.show();
    }

    Integer choose = 0;

    @OnClick(R.id.ivImage)
    public void ivImage(){
        choose = CAMERA_REQUEST_CODE_PROFILE;
        if(EasyPermissions.hasPermissions(parentActivity(), perms)){
            EasyImage.openCamera(this, 0);
        }else{
            EasyPermissions.requestPermissions(this, getString(R.string.message_permission), CAMERA_REQUEST_CODE_KTP, perms);
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
                        mPresenterLogin.updateImage(imageProfile,S.PROFILE);
                        ImageUtils.displayImageRoundFromFile(parentActivity(), imageProfileView, imageFile,null);
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
                mPresenterLogin.updateImage(imageKtp,S.KTP);
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

    @Override
    public void gotoProfile() {
        Intent intent =  new Intent(parentActivity(), MainActivity.class);
        intent.putExtra(MainActivity.FROM,4);
        startActivity(intent);
        parentActivity().finishAffinity();
    }



    String valueJenisGender;

    /*action btn simpan perubahan*/
    @OnClick(R.id.btnSimpan)
    public void btnSimpan(){
        if(wanitaChoose.isChecked()){
            valueJenisGender = S.PEREMPUAN;
        }else if(lakiChoose.isChecked()){
            valueJenisGender = S.LAKI_LAKI;
        }
        mPresenterLogin.updateData(edNama.getText().toString(),edNoTelp.getText().toString(),edTglLahir.getText().toString(),
               valueJenisGender,edNoKtp.getText().toString());
    }

    @OnClick(R.id.ibBack)
    public void back(){
        Intent intent =  new Intent(parentActivity(), MainActivity.class);
        intent.putExtra(MainActivity.FROM,4);
        startActivity(intent);
        parentActivity().finishAffinity();
    }
}
