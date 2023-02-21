package com.example.petmily.viewModel.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.work.OneTimeWorkRequest;
import androidx.work.OutOfQuotaPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            OneTimeWorkRequest request = new OneTimeWorkRequest.Builder (ChatWorker.class).addTag ( "BACKUP_WORKER_TAG" ).build ();
            WorkManager.getInstance ( context ).enqueue ( request );
        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent in = new Intent(context, UndeadService.class);
            context.startForegroundService(in);
        } else {
            Intent in = new Intent(context, ChatService.class);
            context.startService(in);
        }
    }
}