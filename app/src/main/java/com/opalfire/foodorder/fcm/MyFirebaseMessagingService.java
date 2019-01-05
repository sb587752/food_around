package com.opalfire.foodorder.fcm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build.VERSION;
import android.provider.Settings;
import android.provider.Settings.System;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.app.NotificationCompat.InboxStyle;
import android.support.v4.app.NotificationCompat.Style;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.opalfire.foodorder.HomeActivity;
import com.opalfire.foodorder.R;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData() != null) {
            Log.d(TAG, "From: " + remoteMessage.getFrom());
            Log.d(TAG, "Notification Message Body: " + remoteMessage.getData());
            sendNotification(remoteMessage.getData().get("message"));
            return;
        }
        Log.d(TAG, "FCM Notification failed");
    }

    private void sendNotification(String str) {

        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Notification", str);
        PendingIntent activity = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Builder builder = new Builder(this, "PUSH");
        InboxStyle inboxStyle = new InboxStyle();
        inboxStyle.addLine(str);
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        String str3 = "my_channel_01";
        CharSequence charSequence = "Channel human readable title";
        int i = VERSION.SDK_INT >= 24 ? 4 : 0;
        Notification notification = builder.setSmallIcon(R.mipmap.ic_launcher)
                .setTicker(getString(R.string.app_name))
                .setWhen(currentTimeMillis)
                .setContentTitle(getString(R.string.app_name))
                .setContentIntent(activity)
                .setSound(System.DEFAULT_NOTIFICATION_URI)
                .setStyle(inboxStyle)
                .setWhen(currentTimeMillis)
                .setSmallIcon(getNotificationIcon(builder))
                .setContentText(str)
                .setChannelId(str3)
                .setDefaults(6)
                .build();
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
        if (VERSION.SDK_INT >= 26) {
            notificationManager.createNotificationChannel(new NotificationChannel(str3, charSequence, NotificationManager.IMPORTANCE_MIN));
        }
        notificationManager.notify(0, notification);
    }

    private int getNotificationIcon(Builder builder) {
        if (VERSION.SDK_INT < 21) {
            return R.drawable.ic_stat_push;
        }
        builder.setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
        return R.drawable.ic_stat_push;
    }
}
