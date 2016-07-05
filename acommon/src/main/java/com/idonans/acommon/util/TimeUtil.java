package com.idonans.acommon.util;

import android.os.Build;
import android.provider.Settings;

import com.idonans.acommon.AppContext;

/**
 * 时间相关辅助类
 * Created by pengji on 16-7-5.
 */
public class TimeUtil {

    /**
     * 1秒的毫秒数 {@value}
     */
    public static final long MS_SECOND = 1000L;

    /**
     * 1分钟的毫秒数 {@value}
     */
    public static final long MS_MIN = 60L * MS_SECOND;

    /**
     * 1小时的毫秒数 {@value}
     */
    public static final long MS_HOUR = 60L * MS_MIN;

    /**
     * 1天的毫秒数 {@value}
     */
    public static final long MS_DAY = 24L * MS_HOUR;

    /**
     * 判断系统是否开启了自动时间同步
     */
    public static boolean isSystemAutoTime() {
        boolean autoTime;
        if (Build.VERSION.SDK_INT > 16) {
            try {
                autoTime = Settings.Global.getInt(AppContext.getContext().getContentResolver(), Settings.Global.AUTO_TIME) > 0;
            } catch (Settings.SettingNotFoundException e) {
                autoTime = false;
            }
        } else {
            try {
                autoTime = Settings.System.getInt(AppContext.getContext().getContentResolver(), Settings.System.AUTO_TIME) > 0;
            } catch (Settings.SettingNotFoundException e) {
                autoTime = false;
            }
        }

        return autoTime;
    }

    /**
     * 判断系统是否开启了自动时区同步
     */
    public static boolean isSystemAutoTimeZone() {
        boolean autoTime;
        if (Build.VERSION.SDK_INT > 16) {
            try {
                autoTime = Settings.Global.getInt(AppContext.getContext().getContentResolver(), Settings.Global.AUTO_TIME_ZONE) > 0;
            } catch (Settings.SettingNotFoundException e) {
                autoTime = false;
            }
        } else {
            try {
                autoTime = Settings.System.getInt(AppContext.getContext().getContentResolver(), Settings.System.AUTO_TIME_ZONE) > 0;
            } catch (Settings.SettingNotFoundException e) {
                autoTime = false;
            }
        }

        return autoTime;
    }

}
