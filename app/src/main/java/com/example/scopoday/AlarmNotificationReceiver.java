package com.example.scopoday;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.DebugUtils;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;
import java.util.List;

import static android.app.PendingIntent.FLAG_ONE_SHOT;

/**
 * Created by Sascha on 03/01/20.
 */

public class AlarmNotificationReceiver extends BroadcastReceiver {

    public AlarmNotificationReceiver(){

    }

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        MySQLHelper helper = new MySQLHelper(context);
        List<Contactdata> contacts = helper.getAllContacts();
        helper.close();

        Calendar today = Calendar.getInstance();

        if(Intent.ACTION_SCREEN_ON.equals(intent.getAction())){
            Log.d("Zustand", "Screen on");
        }

        Intent myIntent = new Intent(context, MainActivity.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                0,
                myIntent,
                FLAG_ONE_SHOT );

        /*builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Scopoday")
                .setContentIntent(pendingIntent)
                .setContentText("Jemand hat heute Geburstag")
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)
                .setContentInfo("Info");
        */

       /* int channelID = 0;

        for ( Contactdata contact: contacts) {
            channelID++;
            Calendar contactCalendar = Calendar.getInstance();
            contactCalendar.setTime(contact.getBirthdayDate());

            if(today.get(Calendar.DAY_OF_MONTH) == contactCalendar.get(Calendar.DAY_OF_MONTH) &&
                    today.get(Calendar.MONTH) == contactCalendar.get(Calendar.MONTH)){


                String nameOfContact = contact.getName();

                builder = new NotificationCompat.Builder(context, Integer.toString(channelID))
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setWhen(System.currentTimeMillis())
                        .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)
                        .setContentTitle("Geburtstagsmitteilung")
                        .setContentText("Heute hat " + nameOfContact+ " Geburtstag")
                        .setContentInfo("Info");
                int NOTIFICATION_ID = channelID;
                Intent targetIntent = new Intent(context, CheckService.class);
                PendingIntent contentIntent = PendingIntent.getActivity(context, 0, targetIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(contentIntent);
                NotificationManager nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                nManager.notify(NOTIFICATION_ID, builder.build());

                Log.d("noticication Channel id ", Integer.toString(channelID));
                NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(channelID,builder.build());
            }
        }*/


    }
}