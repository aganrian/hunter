package com.example.hunter.service;

import android.util.Log;

import com.example.hunter.data.local.db.HunterDatabase;
import com.example.hunter.di.DaggerAppComponent;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
/*class untuk menampilkan data dari yang di dapatkan dari push */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    @Inject
    HunterDatabase database;

    private CompositeDisposable disposables;

    @Inject
    public MyFirebaseMessagingService() {
        disposables = new CompositeDisposable();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerAppComponent.builder()
                .application(getApplication())
                .build()
                .inject(this);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d(TAG, "onMessageReceived: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {

        }
    }

//    private void sendNotification(String channelUrl, String username, String message) {
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        final String CHANNEL_ID = "CHANNEL_CHAT_WALLO";
//        final String CHANNEL_NAME = "CHAT_WALLO";
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
//            mChannel.setLightColor(Color.GREEN);
//            mChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
//            if (notificationManager != null) {
//                notificationManager.createNotificationChannel(mChannel);
//            }
//        }
//
//        Bundle extras = new Bundle();
//        extras.putString(ChatActivity.EXTRA_CHANNEL_URL, channelUrl);
//
//        Intent intent = new Intent(this, ChatActivity.class);
//        intent.putExtras(extras);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        PendingIntent pendingIntent = TaskStackBuilder.create(this)
//                .addNextIntentWithParentStack(intent)
//                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
//                .setContentTitle(username)
//                .setSmallIcon(R.drawable.ic_notification_light)
//                .setAutoCancel(true)
//                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
//                .setContentText(message)
//                .setSound(defaultSoundUri)
//                .setPriority(Notification.PRIORITY_MAX)
//                .setDefaults(Notification.DEFAULT_ALL)
//                .setContentIntent(pendingIntent);
//
//        if (notificationManager != null) {
//            notificationManager.notify(0, notificationBuilder.build());
//        }
//    }

    @Override
    public void onDestroy() {
        disposables.clear();
        super.onDestroy();
    }
}