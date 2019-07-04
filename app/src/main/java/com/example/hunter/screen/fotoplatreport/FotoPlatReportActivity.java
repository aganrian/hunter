package com.example.hunter.screen.fotoplatreport;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.hunter.screen.login.LoginFragment;
import com.example.hunter.utils.ActivityUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;
import id.oase.indonesia.oasebrdiepa.R;

public class FotoPlatReportActivity extends DaggerAppCompatActivity {

    public static String IMAGE_PATH = "IMAGE_PATH";
    public static String VEHICLE_ID = "VEHICLE_ID";
    public static String NO_POLICE = "NO_POLICE";
    public static String MERK = "MERK";
    public static String TAHUN_KENDARAAN = "TAHUN_KENDARAAN";
    public static String JENIS_KENDARAAN = "JENIS_KENDARAAN";
    public static String LEASING = "LEASING";
    public static String DEBITUR = "DEBITUR";
    public static String STATUS = "STATUS";
    public static String OVERDUE_MONTH = "OVERDUE_MONTH";
    public static String STATUS_HANDLING = "STATUS_HANDLING";
    public static String ID_LAPOR = "ID_LAPOR";

    @Inject
    FotoPlatReportFragment mFragment;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotoplat);
        mUnbinder = ButterKnife.bind(this);

        FotoPlatReportFragment fotoPlatReportFragment = (FotoPlatReportFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if (fotoPlatReportFragment == null) {
            fotoPlatReportFragment = mFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    fotoPlatReportFragment, R.id.fragment_container);
        }
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }


}
