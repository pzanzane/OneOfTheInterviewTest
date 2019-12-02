package com.android.app.mobiliyatest.utility;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class AppExecutor {

    private final Executor networkIO;

    private final Executor mainThread;

    private AppExecutor(Executor networkIO, Executor mainThread) {
        this.networkIO = networkIO;
        this.mainThread = mainThread;
    }

    private static AppExecutor INSTANCE = null;

    public static AppExecutor getINSTANCE(){

        if(INSTANCE == null){
            INSTANCE = new AppExecutor(Executors.newFixedThreadPool(1),
                    new MainThreadExecutor());
        }
        return INSTANCE;
    }

    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());
        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }

    public Executor getNetworkIO() {
        return networkIO;
    }

    public Executor getMainThread() {
        return mainThread;
    }

}
