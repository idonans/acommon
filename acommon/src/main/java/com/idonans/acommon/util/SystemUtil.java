package com.idonans.acommon.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.CheckResult;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;

import com.idonans.acommon.AppContext;
import com.idonans.acommon.data.AppIDManager;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
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

    /**
     * @see #isSoftKeyboardShown(View)
     */
    public static boolean isSoftKeyboardShown(Activity activity) {
        View view = null;
        if (activity != null) {
            Window window = activity.getWindow();
            if (window != null) {
                view = window.getDecorView();
            }
        }
        return isSoftKeyboardShown(view);
    }

    /**
     * @see #isSoftKeyboardShown(View)
     */
    public static boolean isSoftKeyboardShown(Fragment fragment) {
        return isSoftKeyboardShown(getActivityFromFragment(fragment));
    }

    /**
     * 判断当前软键盘是否处于打开状态 (非全屏并且 windowSoftInputMode 为 adjustResize 时有效)
     *
     * @return return false if view is null or not in view tree.
     */
    public static boolean isSoftKeyboardShown(@Nullable View view) {
        if (view == null) {
            return false;
        }

        View rootView = view.getRootView();
        if (rootView == null) {
            return false;
        }

        View contentView = rootView.findViewById(Window.ID_ANDROID_CONTENT);
        if (contentView == null) {
            return false;
        }

        int softKeyboardHeight = DimenUtil.dp2px(100);
        return rootView.getBottom() - contentView.getBottom() > softKeyboardHeight;
    }

    @CheckResult
    public static FragmentActivity getActivityFromFragment(@Nullable Fragment fragment) {
        if (fragment == null) {
            return null;
        }

        FragmentActivity activity = fragment.getActivity();
        if (activity != null) {
            return activity;
        }

        return getActivityFromFragment(fragment.getParentFragment());
    }

    /////

    /**
     * 正确处理，等待拍照结果，在 onActivityResult 中接收结果
     */
    public static final int TAKE_PHOTO_RESULT_OK = 0;
    /**
     * SD卡错误，不能创建用于存储拍照结果的文件
     */
    public static final int TAKE_PHOTO_RESULT_SDCARD_ERROR = 1;
    /**
     * 没有找到系统程序用来处理拍照请求
     */
    public static final int TAKE_PHOTO_RESULT_CAMERA_NOT_FOUND = 2;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({TAKE_PHOTO_RESULT_OK, TAKE_PHOTO_RESULT_SDCARD_ERROR, TAKE_PHOTO_RESULT_CAMERA_NOT_FOUND})
    public @interface TakePhotoResult {
    }

    /**
     * @param outPhotos 如果拍照成功，outPhotos[0] 将用来存储拍照的图
     */
    @TakePhotoResult
    public static int takePhoto(Fragment fragment, int requestCode, File[] outPhotos) {
        File file = FileUtil.createNewTmpFileQuietly("camera", ".jpg", FileUtil.getPublicDCIMDir());
        if (file == null) {
            return TAKE_PHOTO_RESULT_SDCARD_ERROR;
        }

        FileUtil.deleteFileQuietly(file);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));

        List<ResolveInfo> infos = AppContext.getContext().getPackageManager().queryIntentActivities(intent, 0);
        if (infos != null && infos.size() > 0) {
            fragment.startActivityForResult(intent, requestCode);
            outPhotos[0] = file;
            return TAKE_PHOTO_RESULT_OK;
        } else {
            return TAKE_PHOTO_RESULT_CAMERA_NOT_FOUND;
        }
    }

    /**
     * @param outPhotos 如果拍照成功，outPhotos[0] 将用来存储拍照的图
     */
    @TakePhotoResult
    public static int takePhoto(Activity activity, int requestCode, File[] outPhotos) {
        File file = FileUtil.createNewTmpFileQuietly("camera", ".jpg", FileUtil.getPublicDCIMDir());
        if (file == null) {
            return TAKE_PHOTO_RESULT_SDCARD_ERROR;
        }

        FileUtil.deleteFileQuietly(file);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));

        List<ResolveInfo> infos = AppContext.getContext().getPackageManager().queryIntentActivities(intent, 0);
        if (infos != null && infos.size() > 0) {
            activity.startActivityForResult(intent, requestCode);
            outPhotos[0] = file;
            return TAKE_PHOTO_RESULT_OK;
        } else {
            return TAKE_PHOTO_RESULT_CAMERA_NOT_FOUND;
        }
    }

    /////
}
