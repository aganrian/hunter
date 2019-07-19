package com.example.hunter.service;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/*Class service untuk mendapat token dari firebase jika kedepannya di pasang push notif*/
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String token = FirebaseInstanceId.getInstance().getToken();

        Log.d(TAG, "onTokenRefresh: " + token);

//        if (token != null && !token.isEmpty()) {
//            SendBird.registerPushTokenForCurrentUser(token, (pushTokenRegistrationStatus, e) -> {
//                if (e != null) {
//                    e.printStackTrace();
//                    return;
//                }
//
//                if (pushTokenRegistrationStatus == SendBird.PushTokenRegistrationStatus.PENDING ||
//                        pushTokenRegistrationStatus == SendBird.PushTokenRegistrationStatus.ERROR) {
//                    Log.d(TAG, "registerPushNotification: PENDING");
//                } else {
//                    Log.d(TAG, "registerPushNotification: SUCCESS");
//                }
//            });
//        }
    }
}