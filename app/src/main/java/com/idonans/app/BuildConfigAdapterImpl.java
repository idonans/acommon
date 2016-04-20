package com.idonans.app;

import android.util.Log;

import com.idonans.acommon.App;

/**
 * build config adapter for app
 * Created by idonans on 16-4-20.
 */
public class BuildConfigAdapterImpl implements App.BuildConfigAdapter {

    @Override
    public int getVersionCode() {
        return BuildConfig.VERSION_CODE;
    }

    @Override
    public String getVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    @Override
    public String getLogTag() {
        return BuildConfig.APPLICATION_ID;
    }

    @Override
    public int getLogLevel() {
        return Log.DEBUG;
    }

    @Override
    public boolean isDebug() {
        return BuildConfig.DEBUG;
    }

}
