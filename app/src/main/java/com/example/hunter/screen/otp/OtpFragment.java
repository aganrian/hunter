package com.example.hunter.screen.otp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hunter.base.BaseFragment;
import com.example.hunter.custom.CustomDialog;
import com.example.hunter.screen.biodata.BiodataActivity;
import com.example.hunter.screen.changepassword.ChangePasswordActivity;
import com.example.hunter.screen.login.LoginContract;
import com.example.hunter.screen.register.RegisterActivity;
import com.google.android.material.textfield.TextInputEditText;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.OnClick;
import com.example.hunter.R;

import static android.content.Context.INPUT_METHOD_SERVICE;

/*Fragment otp*/
public class OtpFragment extends BaseFragment implements OtpContract.View {

    @Inject
    OtpContract.Presenter mPresenterOtp;

    @Inject
    @Named("USER_ID")
    Integer idUser;

    @Inject
    @Named("USER_EMAIL")
    String userEmail;

    @Inject
    @Named("USER_NAMA")
    String userNama;

    @Inject
    @Named("FROM")
    String from;

    @BindView(R.id.otpEdit1)
    EditText otpEdit1;

    @BindView(R.id.otpEdit2)
    EditText otpEdit2;

    @BindView(R.id.otpEdit3)
    EditText otpEdit3;

    @BindView(R.id.otpEdit4)
    EditText otpEdit4;

    @BindView(R.id.otpEdit5)
    EditText otpEdit5;

    @BindView(R.id.otpEdit6)
    EditText otpEdit6;

    @BindView(R.id.ly_loading_layout)
    RelativeLayout progressBar;

    @BindView(R.id.previewemail)
    TextView previewEmal;

    private String otp = "";


    private EditText[] editTexts;

    @Inject
    public OtpFragment(){

    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_otp;
    }

    @Override
    protected void initView(Bundle state) {
        mPresenterOtp.takeView(this);
        editTexts = new EditText[]{otpEdit1, otpEdit2, otpEdit3, otpEdit4,otpEdit5,otpEdit6};

        otpEdit1.addTextChangedListener(new PinTextWatcher(0));
        otpEdit2.addTextChangedListener(new PinTextWatcher(1));
        otpEdit3.addTextChangedListener(new PinTextWatcher(2));
        otpEdit4.addTextChangedListener(new PinTextWatcher(3));
        otpEdit5.addTextChangedListener(new PinTextWatcher(4));
        otpEdit6.addTextChangedListener(new PinTextWatcher(5));

        otpEdit1.setOnKeyListener(new PinOnKeyListener(0));
        otpEdit2.setOnKeyListener(new PinOnKeyListener(1));
        otpEdit3.setOnKeyListener(new PinOnKeyListener(2));
        otpEdit4.setOnKeyListener(new PinOnKeyListener(3));
        otpEdit5.setOnKeyListener(new PinOnKeyListener(4));
        otpEdit6.setOnKeyListener(new PinOnKeyListener(5));

        previewEmal.setText(userEmail);

    }

    public class PinOnKeyListener implements View.OnKeyListener {

        private int currentIndex;

        PinOnKeyListener(int currentIndex) {
            this.currentIndex = currentIndex;
        }

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                if (editTexts[currentIndex].getText().toString().isEmpty() && currentIndex != 0)
                    editTexts[currentIndex - 1].requestFocus();
            }
            return false;
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

            }
        });
    }
    /*ketika berhasil otp di validasi akan ke layar Biodata jika memang otp di butuhkan saat register baru*/

    @Override
    public void goToIsiProfile() {
        Intent intent = new Intent(parentActivity(), BiodataActivity.class);
        intent.putExtra(BiodataActivity.USER_ID,idUser);
        intent.putExtra(BiodataActivity.USER_EMAIL,userEmail);
        intent.putExtra(BiodataActivity.USER_OTP,otp);
        intent.putExtra(BiodataActivity.USER_NAMA,userNama);
        startActivity(intent);
        parentActivity().finish();
    }

    /*ketika berhasil otp di validasi akan ke layar ganti password jika memang otp di butuhkan saat ganti password*/
    @Override
    public void goToChangePassword() {
        Intent intent = new Intent(parentActivity(), ChangePasswordActivity.class);
        intent.putExtra(ChangePasswordActivity.USER_ID,idUser);
        intent.putExtra(ChangePasswordActivity.USER_EMAIL,userEmail);
        startActivity(intent);
        parentActivity().finish();
    }


    /*action ketika tombol validasi OTP*/
    @OnClick(R.id.nextOtp)
    public void nextOtp(){
        if(editTexts.length<6){
            showErrorMessage(parentActivity().getString(R.string.err_message_fill_otp));
        }else{
            String s = "";

            for(EditText editText : editTexts){
                s = s.concat(editText.getText().toString());
            }

            otp = s;
            mPresenterOtp.sendOtp(idUser,otp,from);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenterOtp.takeView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenterOtp.dropView();
    }

    public class PinTextWatcher implements TextWatcher {

        private int currentIndex;
        private boolean isFirst = false, isLast = false;
        private String newTypedString = "";

        PinTextWatcher(int currentIndex) {
            this.currentIndex = currentIndex;

            if (currentIndex == 0)
                this.isFirst = true;
            else if (currentIndex == editTexts.length - 1)
                this.isLast = true;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            newTypedString = s.subSequence(start, start + count).toString().trim();
        }

        @Override
        public void afterTextChanged(Editable s) {

            String text = newTypedString;

            /* Detect paste event and set first char */
            if (text.length() > 1)
                text = String.valueOf(text.charAt(0)); // TODO: We can fill out other EditTexts

            editTexts[currentIndex].removeTextChangedListener(this);
            editTexts[currentIndex].setText(text);
            editTexts[currentIndex].setSelection(text.length());
            editTexts[currentIndex].addTextChangedListener(this);

            if (text.length() == 1)
                moveToNext();
            else if (text.length() == 0)
                moveToPrevious();
        }

        private void moveToNext() {
            if (!isLast)
                editTexts[currentIndex + 1].requestFocus();

            if (isAllEditTextsFilled() && isLast) { // isLast is optional
                editTexts[currentIndex].clearFocus();
                hideKeyboard();
            }
        }

        private void moveToPrevious() {
            if (!isFirst)
                editTexts[currentIndex - 1].requestFocus();
        }

        private boolean isAllEditTextsFilled() {
            for (EditText editText : editTexts)
                if (editText.getText().toString().trim().length() == 0)
                    return false;
            return true;
        }

        private void hideKeyboard() {
            if (parentActivity().getCurrentFocus() != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) parentActivity().getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(parentActivity().getCurrentFocus().getWindowToken(), 0);
            }
        }

    }

    /*action ketika resend otp di klik*/
    @OnClick(R.id.resendOtp)
    public void resendOtp(){
        mPresenterOtp.resendCodeOtp(idUser);
    }


}
