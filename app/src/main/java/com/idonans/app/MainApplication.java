package com.idonans.app;

import android.app.Application;
import android.util.Log;

import com.idonans.acommon.App;

/**
 * App 入口
 * Created by idonans on 16-4-12.
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        App.init(this, new BuildConfigAdapterImpl());
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
        public int getLogLevel() {
            return Log.DEBUG;
        }

        @Override
        public boolean isDebug() {
            return BuildConfig.DEBUG;
        }
    }

}
