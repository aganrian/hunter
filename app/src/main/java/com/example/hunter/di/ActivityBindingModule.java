package com.example.hunter.di;

import com.example.hunter.screen.alamat.AlamatActivity;
import com.example.hunter.screen.alamat.AlamatModule;
import com.example.hunter.screen.announcement.AnnouncementModule;
import com.example.hunter.screen.biodata.BiodataActivity;
import com.example.hunter.screen.biodata.BiodataModule;
import com.example.hunter.screen.changepassword.ChangePasswordActivity;
import com.example.hunter.screen.changepassword.ChangePasswordModule;
import com.example.hunter.screen.detilhistory.DetilHistoryActivity;
import com.example.hunter.screen.detilhistory.DetilHistoryModule;
import com.example.hunter.screen.editprofile.EditProfileActivity;
import com.example.hunter.screen.editprofile.EditProfileModule;
import com.example.hunter.screen.forgotpassword.ForgotPasswordActivity;
import com.example.hunter.screen.forgotpassword.ForgotPasswordModule;
import com.example.hunter.screen.fotoplat.FotoPlatModule;
import com.example.hunter.screen.fotoplatreport.FotoPlatReportActivity;
import com.example.hunter.screen.fotoplatreport.FotoPlatReportModule;
import com.example.hunter.screen.history.HistoryModule;
import com.example.hunter.screen.historyredeem.HistoryRedeemModule;
import com.example.hunter.screen.historyreport.HistoryReportModule;
import com.example.hunter.screen.home.HomeModule;
import com.example.hunter.screen.main.MainActivity;
import com.example.hunter.screen.main.MainModule;
import com.example.hunter.screen.login.LoginActivity;
import com.example.hunter.screen.login.LoginModule;
import com.example.hunter.screen.otp.OtpActivity;
import com.example.hunter.screen.otp.OtpModule;
import com.example.hunter.screen.profile.ProfileModule;
import com.example.hunter.screen.register.RegisterActivity;
import com.example.hunter.screen.register.RegisterModule;
import com.example.hunter.screen.splash.SplashActivity;
import com.example.hunter.screen.splash.SplashModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = LoginModule.class)
    abstract LoginActivity loginActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = SplashModule.class)
    abstract SplashActivity splashActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = RegisterModule.class)
    abstract RegisterActivity registerActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = OtpModule.class)
    abstract OtpActivity otpActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = BiodataModule.class)
    abstract BiodataActivity biodataActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = ForgotPasswordModule.class)
    abstract ForgotPasswordActivity forgotPasswordActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = ChangePasswordModule.class)
    abstract ChangePasswordActivity changePasswordActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = {MainModule.class, HomeModule.class, HistoryModule.class,FotoPlatModule.class,
            HistoryReportModule.class, HistoryRedeemModule.class, AnnouncementModule.class, ProfileModule.class})
    abstract MainActivity homeActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = FotoPlatReportModule.class)
    abstract FotoPlatReportActivity fotoPlatReportActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = DetilHistoryModule.class)
    abstract DetilHistoryActivity detilHistoryActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = EditProfileModule.class)
    abstract EditProfileActivity editProfileActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = AlamatModule.class)
    abstract AlamatActivity alamatActivity();

}