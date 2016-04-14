package com.idonans.acommon.util;

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

}
