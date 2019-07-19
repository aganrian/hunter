package com.example.hunter.base;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hunter.R;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerFragment;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/*class base yang akan menjadi pusat / induk dari fragment*/
public abstract class BaseFragment extends DaggerFragment implements EasyPermissions.PermissionCallbacks {

    protected static final int PERMISSION_REQUEST_CODE = 2000;

    private AppCompatActivity mActivity;

    private Unbinder mUnbinder;

    protected abstract int getLayoutView();

    protected abstract void initView(Bundle state);

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = ((AppCompatActivity) context);
    }

    @androidx.annotation.Nullable
    @Override
    public View onCreateView(@androidx.annotation.NonNull LayoutInflater inflater, @androidx.annotation.Nullable ViewGroup container, @androidx.annotation.Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(getLayoutView(), container, false);
        mUnbinder = ButterKnife.bind(this, root);
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onActivityCreated(@androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(savedInstanceState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @androidx.annotation.NonNull String[] permissions, @androidx.annotation.NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @androidx.annotation.NonNull List<String> perms) {
        // Do Something
        Log.e("cccc", "onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, @androidx.annotation.NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(mActivity, perms)) {
            new AppSettingsDialog.Builder(mActivity).build().show();
        }
    }


    @AfterPermissionGranted(PERMISSION_REQUEST_CODE)
    public void needPermissions(String[] permissions) {
        if (!EasyPermissions.hasPermissions(mActivity, permissions)) {
            EasyPermissions.requestPermissions(mActivity, getString(R.string.message_permission),
                    PERMISSION_REQUEST_CODE, permissions);
        }
    }



    public AppCompatActivity parentActivity() {
        return mActivity;
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }




}
