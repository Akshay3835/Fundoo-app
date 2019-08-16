package com.bridgelabz.fundoo.BroadCastReciever;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.bridgelabz.fundoo.add_note_page.View.AddNoteActivity;

import static com.bridgelabz.fundoo.application.ApplicationClass.Channel_1;

public class AlarmReceiever extends BroadcastReceiver {
    private static final String TAG = "AlarmReceiever.class";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent notificationIntent = new Intent(context, AddNoteActivity.class);


        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);

        stackBuilder.addParentStack(AddNoteActivity.class);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(100,
                PendingIntent.FLAG_UPDATE_CURRENT);



        Notification.Builder builder = new Notification.Builder(context, Channel_1);

        Notification notification = builder.setContentTitle("Fundoo App Notification")
                .setContentText("title")
                .setTicker("New Message Alert!")
                .setPriority(Notification.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setSmallIcon(android.R.mipmap.sym_def_app_icon)
                .build();


            NotificationManager notificationManager = (NotificationManager) context.
                    getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notification);


        Log.e(TAG, "Notification received");

    }

}




