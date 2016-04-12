package com.idonans.app;

import android.app.Application;

import com.idonans.acommon.AppContext;

/**
 * Created by idonans on 16-4-12.
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppContext.setContext(this);
    }

}
