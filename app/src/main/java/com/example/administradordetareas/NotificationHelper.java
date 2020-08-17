package com.example.administradordetareas;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import androidx.core.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper
{
    public static  final  String channelID = "canal";
    public static  final  String channelName = "canalDeNotificacion";

    private NotificationManager notificationManager;

    public NotificationHelper(Context base)
    {
        super(base);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O )
        {
            createChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    public void createChannel()
    {
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLightColor(R.color.colorPrimary);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager()
    {
        if (notificationManager == null)
        {
            notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return  notificationManager;
    }

    public NotificationCompat.Builder getChannelNotification(String Titulo, String Mensaje)
    {
        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle(Titulo).setContentText(Mensaje)
                .setSmallIcon(R.drawable.ic_notificacion);
    }
}
