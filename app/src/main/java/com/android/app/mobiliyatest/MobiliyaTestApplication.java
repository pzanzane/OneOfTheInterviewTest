package com.android.app.mobiliyatest;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

public class MobiliyaTestApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
