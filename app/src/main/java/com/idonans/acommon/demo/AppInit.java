package com.idonans.acommon.demo;

import android.content.Context;
import android.util.Log;

import com.idonans.acommon.App;

/**
 * Created by idonans on 2016/11/18.
 */

public final class AppInit {

    private static final String TAG = "AppInit";
    private static final Object LOCK = new Object();
    private static boolean sInit;

    public static void init(Context context) {
        if (sInit) {
            return;
        }

        synchronized (LOCK) {
            if (!sInit) {
                new App.Config.Builder()
                        .setContext(context)
                        .setBuildConfigAdapter(new BuildConfigAdapterImpl())
                        .build().init();
                sInit = true;
            }
        }
    }

    private static class BuildConfigAdapterImpl implements App.BuildConfigAdapter {

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
        public String getPublicSubDirName() {
            return BuildConfig.APPLICATION_ID;
        }

        @Override
        public String getChannel() {
            return "default_channel";
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

}
