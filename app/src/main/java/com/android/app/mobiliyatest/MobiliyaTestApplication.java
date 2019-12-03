package com.android.app.mobiliyatest;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

public class MobiliyaTestApplication extends Application {

    private static Context context = null;
    public static Context getAppContext(){
        return context;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Fresco.initialize(this);
    }
}
