package com.example.petmily.viewModel.service;

import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.work.CoroutineWorker;
import androidx.work.WorkerParameters;

import kotlin.coroutines.Continuation;

public class ChatWorker extends CoroutineWorker {
    public static boolean isRunning = false;
    Context context;

    public ChatWorker (@NonNull Context context, @NonNull WorkerParameters workerParams ) {
        super ( context, workerParams );
        this.context = context;
    }
    @Nullable
    @Override
    public Object doWork(@NonNull Continuation<? super Result> continuation) {
        if(isRunning) {
            registerRestartAlarm();
            Intent in = new Intent(context, ChatService.class);
            context.startService(in);
        }

        return Result.success();
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
}
