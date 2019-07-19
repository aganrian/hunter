package com.example.hunter.custom;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hunter.R;

public class CustomDialog {

    private OnDialogClickBtnListener mListener;

    public void showDialog(Activity activity,String title,String content,String footer,Boolean isDouble, boolean isHeader, boolean isBottom) {

        final Dialog dialog = new Dialog(activity,R.style.CustomAlertDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        TextView mTitle = dialog.findViewById(R.id.textTitle);


        if(isHeader){
            mTitle.setVisibility(View.VISIBLE);
            mTitle.setText(title);
        }else{
            mTitle.setVisibility(View.GONE);
        }


        TextView mContent = dialog.findViewById(R.id.textContent);
        mContent.setText(content);

        RelativeLayout relDouble = dialog.findViewById(R.id.lyButtonOcr);
        RelativeLayout lyButtonOk = dialog.findViewById(R.id.lyButtonOk);

        if(isDouble){
            relDouble.setVisibility(View.VISIBLE);
            lyButtonOk.setVisibility(View.GONE);
        }else{
            relDouble.setVisibility(View.GONE);
            lyButtonOk.setVisibility(View.VISIBLE);
        }

        Button btnOk = dialog.findViewById(R.id.btnOke);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(mListener!=null)
                    mListener.onOkListener();
            }
        });

        Button btnTidak = dialog.findViewById(R.id.btnTidak);
        btnTidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(mListener!=null)
                    mListener.onNegativeListener();
            }
        });

        Button btnYa = dialog.findViewById(R.id.btnYa);
        btnYa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(mListener!=null)
                    mListener.onPositiveLisneter();
            }
        });

        dialog.show();
    }

    public void setOnDialogResultListener(OnDialogClickBtnListener listener) {
        this.mListener = listener;
    }


    public interface OnDialogClickBtnListener{
        public abstract void onPositiveLisneter();
        public abstract void onNegativeListener();
        public abstract void onOkListener();
    }


}
