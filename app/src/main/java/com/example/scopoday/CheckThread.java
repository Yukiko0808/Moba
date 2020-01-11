package com.example.scopoday;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;
import java.util.List;

public class CheckThread extends Thread {

    Context context;

    CheckThread(Context context){
        this.context = context;
    }

    public void run(){
        while(true){

            try{

                MySQLHelper helper = new MySQLHelper(context);
                List<Contactdata> contacts = helper.getAllContacts();
                helper.close();

                Calendar today = Calendar.getInstance();

                for ( Contactdata contact: contacts) {
                    Log.i("CheckThread", "Prüfe Contakt");

                    Calendar contactCalendar = Calendar.getInstance();
                    contactCalendar.setTime(contact.getBirthdayDate());

                    if(today.get(Calendar.DAY_OF_MONTH) == contactCalendar.get(Calendar.DAY_OF_MONTH) &&
                        today.get(Calendar.MONTH) == contactCalendar.get(Calendar.MONTH)){
                        String nameOfContact = contact.getName();

                        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "10")
                                .setDefaults(Notification.DEFAULT_ALL)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("Geburtstagsmitteilung")
                                .setContentText("Heute hat " + nameOfContact+ " Geburtstag")
                                .setContentInfo("Info");
                        int NOTIFICATION_ID = 12345;
                        Intent targetIntent = new Intent(context, CheckService.class);
                        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, targetIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                        builder.setContentIntent(contentIntent);
                        NotificationManager nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                        nManager.notify(NOTIFICATION_ID, builder.build());
                    }
                }

                Thread.sleep(43200000);
            } catch (InterruptedException e){
                Toast.makeText(context, "Thread interrupted", Toast.LENGTH_LONG).show();
            }
        }
    }
}
