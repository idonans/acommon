package com.idonans.acommon.demo;

import android.app.Application;

/**
 * App 入口
 * Created by idonans on 16-4-12.
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppInit.init(this);
    }

}
