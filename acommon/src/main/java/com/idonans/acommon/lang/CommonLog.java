package com.idonans.acommon.lang;

import android.support.annotation.Nullable;
import android.util.Log;

/**
 * log 处理
 * Created by idonans on 16-4-13.
 */
public class CommonLog {

    private static String sLogTag = "common";
    private static int sLogLevel = Log.DEBUG;

    public static void setLogLevel(int logLevel) {
        sLogLevel = logLevel;
    }

    public static void setLogTag(String logTag) {
        sLogTag = logTag;
    }

    public static void d(@Nullable Object message) {
        if (sLogLevel <= Log.DEBUG) {
            Log.d(sLogTag, String.valueOf(message));
        }
    }

    public static void e(@Nullable Object message) {
        if (sLogLevel <= Log.ERROR) {
            Log.e(sLogTag, String.valueOf(message));
        }
    }

}
