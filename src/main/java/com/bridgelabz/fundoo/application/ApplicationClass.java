package com.bridgelabz.fundoo.application;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;


public class ApplicationClass extends Application
{
    public static final String Channel_1 ="CHANNEL_1";

    @Override
    public void onCreate()
    {
        super.onCreate();
        createNotificationChannel();

    }

    private void createNotificationChannel()
    {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel
                    (Channel_1,"Channel_1",NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Set Description 1");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}



