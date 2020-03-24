package com.motofit.app;

import android.annotation.SuppressLint;
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
        String message = Objects.requireNonNull(remoteMessage.getNotification()).getBody();
        String title = Objects.requireNonNull(remoteMessage.getNotification()).getTitle();
        Intent i = new Intent(this, history.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi = PendingIntent
                .getActivity(this, 0, i, 0);
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this);
        notification.setContentTitle(title);
        notification.setContentText(message);
        notification.setSmallIcon(R.drawable.motofit_img);
        notification.setAutoCancel(true);
        notification.setContentIntent(pi);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(0, notification.build());
        }
    }
}
