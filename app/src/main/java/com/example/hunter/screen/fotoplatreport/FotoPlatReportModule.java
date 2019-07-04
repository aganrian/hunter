package com.example.hunter.screen.fotoplatreport;


import android.os.Bundle;

import com.example.hunter.di.ActivityScoped;
import com.example.hunter.di.FragmentScoped;
import com.example.hunter.screen.biodata.BiodataActivity;
import com.example.hunter.screen.fotoplat.FotoPlatFragment;
import com.example.hunter.screen.login.LoginActivity;
import com.example.hunter.screen.login.LoginContract;
import com.example.hunter.screen.login.LoginFragment;
import com.example.hunter.screen.login.LoginPresenter;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FotoPlatReportModule {

    @ActivityScoped
    @Binds
    abstract FotoPlatReportContract.Presenter fotoPlatReportPresenter(FotoPlatReportPresenter presenter);

    @FragmentScoped
    @ContributesAndroidInjector
    abstract FotoPlatReportFragment fotoPlatReportFragment();

    @Provides
    @ActivityScoped
    static Bundle provideExtras(FotoPlatReportActivity activity) {
        return activity.getIntent().getExtras();
    }

    @Provides
    @Named("IMAGE_PATH")
    @ActivityScoped
    static String proviceImagePath(FotoPlatReportActivity activity) {
        return activity.getIntent().getExtras().getString(FotoPlatReportActivity.IMAGE_PATH);
    }

    @Provides
    @Named("VEHICLE_ID")
    @ActivityScoped
    static Integer proviceVehicleId(FotoPlatReportActivity activity) {
        return activity.getIntent().getExtras().getInt(FotoPlatReportActivity.VEHICLE_ID);
    }

    @Provides
    @Named("NO_POLICE")
    @ActivityScoped
    static String proviceNoPolice(FotoPlatReportActivity activity) {
        return activity.getIntent().getExtras().getString(FotoPlatReportActivity.NO_POLICE);
    }

    @Provides
    @Named("MERK")
    @ActivityScoped
    static String proviceMerk(FotoPlatReportActivity activity) {
        return activity.getIntent().getExtras().getString(FotoPlatReportActivity.MERK);
    }

    @Provides
    @Named("TAHUN_KENDARAAN")
    @ActivityScoped
    static Integer proviceTahunKendaraan(FotoPlatReportActivity activity) {
        return activity.getIntent().getExtras().getInt(FotoPlatReportActivity.TAHUN_KENDARAAN);
    }

    @Provides
    @Named("JENIS_KENDARAAN")
    @ActivityScoped
    static String proviceJenisKendaraan(FotoPlatReportActivity activity) {
        return activity.getIntent().getExtras().getString(FotoPlatReportActivity.JENIS_KENDARAAN);
    }


    @Provides
    @Named("LEASING")
    @ActivityScoped
    static String proviceLeasing(FotoPlatReportActivity activity) {
        return activity.getIntent().getExtras().getString(FotoPlatReportActivity.LEASING);
    }

    @Provides
    @Named("DEBITUR")
    @ActivityScoped
    static String proviceNamaDebitur(FotoPlatReportActivity activity) {
        return activity.getIntent().getExtras().getString(FotoPlatReportActivity.DEBITUR);
    }

    @Provides
    @Named("STATUS")
    @ActivityScoped
    static String provideUserStatus(FotoPlatReportActivity activity) {
        return activity.getIntent().getExtras().getString(FotoPlatReportActivity.STATUS);
    }


    @Provides
    @Named("OVERDUE_MONTH")
    @ActivityScoped
    static Integer provideOverDueMonth(FotoPlatReportActivity activity) {
        return activity.getIntent().getExtras().getInt(FotoPlatReportActivity.OVERDUE_MONTH);
    }

    @Provides
    @Named("STATUS_HANDLING")
    @ActivityScoped
    static String provideStatusHandling(FotoPlatReportActivity activity) {
        return activity.getIntent().getExtras().getString(FotoPlatReportActivity.STATUS_HANDLING);
    }

    @Provides
    @Named("ID_LAPOR")
    @ActivityScoped
    static Integer proviceIdLapor(FotoPlatReportActivity activity) {
        return activity.getIntent().getExtras().getInt(FotoPlatReportActivity.ID_LAPOR);
    }
}