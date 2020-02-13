package com.example.scopoday;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.app.PendingIntent.FLAG_ONE_SHOT;

public class CheckService extends Service {
    private volatile boolean hasStarted = false;
    protected Handler handler;
    protected Toast mToast;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        //Toast.makeText(getApplicationContext(), "Service onCreate", Toast.LENGTH_LONG).show();
        if(!hasStarted) {
            hasStarted = true;
            Log.i("CheckService", "Start Thread");
            CheckThread thread = new CheckThread(getApplicationContext());
            thread.start();
        }
        Log.i("CheckService", "StartCommand Ende");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       // Toast.makeText(getApplicationContext(), "Service destroyed", Toast.LENGTH_LONG).show();
        Log.i("CheckService", "Destroy");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return android.app.Service.START_NOT_STICKY;
    }
}
