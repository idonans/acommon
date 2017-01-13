package com.idonans.acommon.demo;

import android.app.Application;

import com.idonans.acommon.lang.Threads;

/**
 * App 入口
 * Created by idonans on 16-4-12.
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppInit.init(this);

        Threads.sleepQuietly(3000);
    }

}
