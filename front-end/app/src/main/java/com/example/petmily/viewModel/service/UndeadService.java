package com.example.petmily.viewModel.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.petmily.R;
import com.example.petmily.view.MainActivity;

import java.util.Calendar;

public class UndeadService extends Service{
    public static boolean isRunning = false;

    private NotificationManager manager;
    private Context context;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        context = getApplicationContext();
        if(isRunning)
            initializeNotification();

        return START_STICKY;
    }

    public void initializeNotification() {
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "Undead_Channel")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("")
                .setContentText("")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Notification notification = builder.build();

        startForeground(1, notification);
        nm.notify(1, notification);
        nm.cancel(1);
        startService(new Intent(this, ChatService.class));
        stopForeground(true);
        stopSelf();
    }

    @Override
    public void onDestroy() {
        if(isRunning)
            registerRestartAlarm();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        if(isRunning)
            registerRestartAlarm();
        super.onTaskRemoved(rootIntent);
    }
    public void createNotificationChannel()
    {
        // 기기(device)의 SDK 버전 확인 ( SDK 26 버전 이상인지 - VERSION_CODES.O = 26)
        if(android.os.Build.VERSION.SDK_INT
                >= android.os.Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel("Undead_Channel"
                    ,"Test Notification",manager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from Mascot");
            manager.createNotificationChannel(notificationChannel);
        }
    }
    private void registerRestartAlarm(){
        Intent intent = new Intent(context,AlarmReceiver.class);
        intent.setAction("ACTION.RESTART.PersistentService");
        PendingIntent sender = PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_MUTABLE);

        long firstTime = SystemClock.elapsedRealtime();
        firstTime += 5*1000;

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, firstTime, sender);
    }
    public void unregisterRestartAlarm(){
        Intent intent = new Intent(UndeadService.this,UndeadService.class);
        intent.setAction("ACTION.RESTART.PersistentService");
        PendingIntent sender = PendingIntent.getBroadcast(UndeadService.this,0,intent,PendingIntent.FLAG_MUTABLE);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.cancel(sender);

    }
}