package com.example.hunter.utils;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/*Helper untuk membantu transaksi antar Activity dan fragment*/
public class ActivityUtils {

    public static void addFragmentToActivity(FragmentManager fragmentManager,
                                             Fragment fragment, int frameId) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }


    public static void addFragmentToActivityWithBundle(FragmentManager fragmentManager,
                                                       Fragment fragment, int frameId, Bundle bundle) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (bundle != null) fragment.setArguments(bundle);
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    public static void addFragmentToActivityStateLost(FragmentManager fragmentManager,
                                                      Fragment fragment, int frameId) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commitAllowingStateLoss();
    }

}
