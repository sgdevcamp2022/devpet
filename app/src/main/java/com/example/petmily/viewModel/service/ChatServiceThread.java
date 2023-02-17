package com.example.petmily.viewModel.service;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


public class ChatServiceThread extends Thread {
    Handler handler;
    boolean isRun = true;



    public ChatServiceThread(Handler handler) {
        this.handler = handler;
    }
    public void stopForever() {
        synchronized (this) {
            this.isRun = false;
        }
    }
    public void run() {
        //반복적으로 수행할 작업을 한다.
        while (isRun) {
            try {
                Thread.sleep( 5000 );
                //getMessage() 챗 리스트 가져오기

                Message message = handler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putString("ㅁㄴㅇ", "ㅁㄴㅇ");
                message.setData(bundle);
                handler.sendMessage(message);
                //Log.e("스레드 실행" , "실행 중");


            } catch (Exception e) {
            }
        }
    }
}