package com.idonans.acommon.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import com.idonans.acommon.AppContext;
import com.idonans.acommon.data.AppIDManager;

import java.util.List;

/**
 * 一些系统相关辅助类
 * Created by idonans on 16-4-14.
 */
public class SystemUtil {

    /**
     * @see AppIDManager
     */
    public static String getAppID() {
        return AppIDManager.getInstance().getAppID();
    }

    /**
     * 成功打开软件商店(会尝试定位到指定软件)返回true, 如果没有安装任何软件商店, 返回false.
     */
    public static boolean openMarket(String packageName) {
        String url = "market://details?id=" + packageName;
        return openView(url);
    }

    /**
     * 使用系统默认方式打开指定 url, 处理成功返回true，否则返回false. 例如可以用来唤起系统浏览器浏览指定 url 的网页。
     */
    public static boolean openView(String url) {
        try {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            List<ResolveInfo> infos = AppContext.getContext().getPackageManager().queryIntentActivities(intent, 0);
            if (infos != null && infos.size() > 0) {
                AppContext.getContext().startActivity(intent);
                return true;
            } else {
                return false;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取当前进程允许的最大 heap size(字节数). 仅 Java 部分的内容受此系统设置限制， native 层的内容消耗受手机内存容量限制。
     * 容量超过此值会出现 OOM 错误。
     * 如 手机 CHM-UL00 的 heap size 是 268435456 byte (256M), 该手机的配置是 2G 内存，16G 存储空间， 1280x720 分辨率, Android 4.4.2 系统
     */
    public static long getMaxHeapSize() {
        ActivityManager am = (ActivityManager) AppContext.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        return am.getMemoryClass() * 1024L * 1024L;
    }

}
