package com.idonans.acommon;

import android.content.Context;

import com.idonans.acommon.data.FrescoManager;
import com.idonans.acommon.lang.CommonLog;

/**
 * 在 App 启动时初始化, 通常在 Application#onCreate, ContentProvider#onCreate
 * Created by idonans on 16-4-18.
 */
public class App {

    private static boolean sInitCalled;
    private static BuildConfigAdapter sBuildConfigAdapter;

    public static void init(Config config) {
        synchronized (App.class) {
            if (sInitCalled) {
                return;
            }
            sInitCalled = true;
        }

        internalInit(config);
    }

    private static void internalInit(Config config) {
        AppContext.setContext(config.mContext);
        sBuildConfigAdapter = config.mBuildConfigAdapter;

        CommonLog.setLogLevel(sBuildConfigAdapter.getLogLevel());
        CommonLog.setLogTag(sBuildConfigAdapter.getLogTag());

        // 配置 fresco
        if (config.mUseFresco) {
            FrescoManager.getInstance();
        }
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

    private App() {
        // ignore
    }


    public static class Config {

        private Context mContext;
        private BuildConfigAdapter mBuildConfigAdapter;
        private boolean mUseFresco;

        private Config() {
        }

        public static class Builder {

            private Context mContext;
            private BuildConfigAdapter mBuildConfigAdapter;
            private boolean mUseFresco = true;

            public Builder setContext(Context context) {
                mContext = context;
                return this;
            }

            public Builder setBuildConfigAdapter(BuildConfigAdapter buildConfigAdapter) {
                mBuildConfigAdapter = buildConfigAdapter;
                return this;
            }

            public Builder setUseFresco(boolean useFresco) {
                mUseFresco = useFresco;
                return this;
            }

            public Config build() {
                if (mContext == null) {
                    throw new IllegalArgumentException("context not set");
                }

                if (mBuildConfigAdapter == null) {
                    throw new IllegalArgumentException("build config adapter not set");
                }

                Config config = new Config();
                config.mContext = this.mContext;
                config.mBuildConfigAdapter = this.mBuildConfigAdapter;
                config.mUseFresco = this.mUseFresco;
                return config;
            }
        }
    }

}
