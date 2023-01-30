package com.example.petmily;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.kakao.sdk.common.KakaoSdk;

public class GlobalApplication extends Application {
    GlobalApplication instance;

    @Override
    public void onCreate()
    {
        super.onCreate();
        KakaoSdk.init(this, "c18201f7adf39676e9db4bc289926e00");
        instance = this;

    }



}
