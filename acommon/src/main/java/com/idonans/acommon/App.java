package com.idonans.acommon;

import android.content.Context;

import com.idonans.acommon.lang.CommonLog;

/**
 * 在 App 启动时初始化, 通常在 Application#onCreate, ContentProvider#onCreate
 * Created by idonans on 16-4-18.
 */
public class App {

    private static boolean sInitCalled;
    private static BuildConfigAdapter sBuildConfigAdapter;

    public static void init(Context context, BuildConfigAdapter buildConfigAdapter) {
        synchronized (App.class) {
            if (sInitCalled) {
                return;
            }
            sInitCalled = true;
        }

        AppContext.setContext(context);
        sBuildConfigAdapter = buildConfigAdapter;

        internalInit();
    }

    private static void internalInit() {
        CommonLog.setLogLevel(sBuildConfigAdapter.getLogLevel());
        CommonLog.setLogTag(sBuildConfigAdapter.getLogTag());
    }

    public static BuildConfigAdapter getBuildConfigAdapter() {
        return sBuildConfigAdapter;
    }

    /**
     * 引用当前 application 所在的 BuildConfig 中的值。 如 apply plugin: 'com.android.application' 所在
     * module 中的 BuildConfig
     */
    public interface BuildConfigAdapter {
        int getVersionCode();

        String getVersionName();

        String getLogTag();

        int getLogLevel();

        boolean isDebug();
    }

}
