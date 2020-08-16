package com.example.administradordetareas;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.util.ResourceBundle;

public class AlertReceiver extends BroadcastReceiver
{
    private String Titulo = "Aviso ";
    private String Mensaje = "Tiene una tarea pendiente ahora ";

    @Override
    public void onReceive(Context context, Intent intent)
    {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification(Titulo, Mensaje);
        notificationHelper.getManager().notify(1, nb.build());
    }
}
