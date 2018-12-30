package com.opalfire.orderaround.fcm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build.VERSION;
import android.provider.Settings.System;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.app.NotificationCompat.InboxStyle;
import android.support.v4.app.NotificationCompat.Style;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.opalfire.orderaround.HomeActivity;
import com.opalfire.orderaround.R;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";

    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData() != null) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("From: ");
            stringBuilder.append(remoteMessage.getFrom());
            Log.d(str, stringBuilder.toString());
            str = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Notification Message Body: ");
            stringBuilder.append(remoteMessage.getData());
            Log.d(str, stringBuilder.toString());
            sendNotification((String) remoteMessage.getData().get("message"));
            return;
        }
        Log.d(TAG, "FCM Notification failed");
    }

    private void sendNotification(String str) {
        String str2 = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("messageBody ");
        stringBuilder.append(str);
        Log.d(str2, stringBuilder.toString());
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.setFlags(603979776);
        intent.putExtra("Notification", str);
        PendingIntent activity = PendingIntent.getActivity(this, 0, intent, 134217728);
        Builder builder = new Builder(this, "PUSH");
        Style inboxStyle = new InboxStyle();
        inboxStyle.addLine(str);
        long currentTimeMillis = System.currentTimeMillis();
        String str3 = "my_channel_01";
        CharSequence charSequence = "Channel human readable title";
        int i = VERSION.SDK_INT >= 24 ? 4 : 0;
        str = builder.setSmallIcon(R.mipmap.ic_launcher).setTicker(getString(R.string.app_name)).setWhen(currentTimeMillis).setContentTitle(getString(R.string.app_name)).setContentIntent(activity).setSound(System.DEFAULT_NOTIFICATION_URI).setStyle(inboxStyle).setWhen(currentTimeMillis).setSmallIcon(getNotificationIcon(builder)).setContentText(str).setChannelId(str3).setDefaults(6).build();
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService("notification");
        if (VERSION.SDK_INT >= 26) {
            notificationManager.createNotificationChannel(new NotificationChannel(str3, charSequence, i));
        }
        notificationManager.notify(0, str);
    }

    private int getNotificationIcon(Builder builder) {
        if (VERSION.SDK_INT < 21) {
            return R.drawable.ic_stat_push;
        }
        builder.setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
        return R.drawable.ic_stat_push;
    }
}
