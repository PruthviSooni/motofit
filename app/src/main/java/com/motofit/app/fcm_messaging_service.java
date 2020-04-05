package com.motofit.app;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Objects;

@SuppressLint({"MissingFirebaseInstanceTokenRefresh", "Registered"})
public class fcm_messaging_service extends FirebaseMessagingService {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String message = Objects.requireNonNull(remoteMessage.getNotification()).getBody();
        String title = Objects.requireNonNull(remoteMessage.getNotification()).getTitle();
        Intent i = new Intent(this, history.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_ONE_SHOT);
        String channelId = "Default";
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, channelId);
        notification.setContentTitle(title);
        notification.setContentText(message);
        notification.setSmallIcon(R.drawable.app_icon);
        notification.setAutoCancel(true);
        notification.setContentIntent(pi);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
        if (notificationManager != null) {
            notificationManager.notify(0, notification.build());
        }
    }
}
