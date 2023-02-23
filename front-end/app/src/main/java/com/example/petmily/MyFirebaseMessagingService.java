package com.example.petmily;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.petmily.view.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public MyFirebaseMessagingService() {
        super();
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getData().size() > 0) {
            showNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("body"));
        }
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }



    public void showNotification(String title, String message) {
        //팝업 터치시 이동할 액티비티를 지정합니다.
        Intent intent = new Intent(this, MainActivity.class);
        //알림 채널 아이디 : 본인 하고싶으신대로...
        String channel_id = "CHN_ID";
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE);

        //기본 사운드로 알림음 설정. 커스텀하려면 소리 파일의 uri 입력
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channel_id)
                .setSmallIcon(R.drawable.icon)
                .setSound(uri)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000}) //알림시 진동 설정 : 1초 진동, 1초 쉬고, 1초 진동
                .setOnlyAlertOnce(true) //동일한 알림은 한번만.. : 확인 하면 다시 울림
                .setContentIntent(pendingIntent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) { //안드로이드 버전이 커스텀 알림을 불러올 수 있는 버전이면
            //커스텀 레이아웃 호출
            builder = builder.setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.drawable.icon); //커스텀 레이아웃에 사용된 로고 파일과 동일하게..
            //builder = builder.setContent(getCustomDesign(title, message));
        } else { //아니면 기본 레이아웃 호출
            builder = builder.setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.drawable.icon); //커스텀 레이아웃에 사용된 로고 파일과 동일하게..
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //알림 채널이 필요한 안드로이드 버전을 위한 코드
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channel_id, "CHN_NAME", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setSound(uri, null);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        //알림 표시 !
        notificationManager.notify(0, builder.build());
    }


}
