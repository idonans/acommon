package com.idonans.acommon.util;

import android.os.Environment;
import android.support.annotation.CheckResult;
import android.text.TextUtils;

import com.idonans.acommon.AppContext;
import com.idonans.acommon.data.ProcessManager;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * 文件操作相关辅助类
 * Created by idonans on 16-4-15.
 */
public class FileUtil {

    private static final String TAG = "FileUtil";

    /**
     * 获得一个进程安全的缓存目录(在 private cache目录下的一个与进程名相关的目录, 如果目录不存在，会尝试创建)
     * <p/>
     * 不能获得这样一个目录，返回 null.
     */
    @CheckResult
    public static File getCacheDir() {
        File cacheDir = AppContext.getContext().getCacheDir();
        if (cacheDir == null) {
            return null;
        }
        File processCacheDir = new File(cacheDir, ProcessManager.getInstance().getProcessTag());
        if (createDir(processCacheDir)) {
            return processCacheDir;
        }
        return null;
    }

    /**
     * 得到一个在系统拍照目录下，以当前应用标识为子文件夹名的目录(如果目录不存在则尝试创建)。
     * <p/>
     * 不能获得这样一个目录，返回 null.
     */
    @CheckResult
    public static File getPublicDCIMDir() {
        return getPublicDir(Environment.DIRECTORY_DCIM);
    }

    /**
     * 得到一个在系统图片目录下，以当前应用标识为子文件夹名的目录(如果目录不存在则尝试创建)。
     * <p/>
     * 不能获得这样一个目录，返回 null.
     */
    @CheckResult
    public static File getPublicPictureDir() {
        return getPublicDir(Environment.DIRECTORY_PICTURES);
    }

    /**
     * 得到一个在系统下载目录下，以当前应用标识为子文件夹名的目录(如果目录不存在则尝试创建)。
     * <p/>
     * 不能获得这样一个目录，返回 null.
     */
    @CheckResult
    public static File getPublicDownloadDir() {
        return getPublicDir(Environment.DIRECTORY_DOWNLOADS);
    }

    /**
     * 得到一个在系统视频目录下，以当前应用标识为子文件夹名的目录(如果目录不存在则尝试创建)。
     * <p/>
     * 不能获得这样一个目录，返回 null.
     */
    @CheckResult
    public static File getPublicMovieDir() {
        return getPublicDir(Environment.DIRECTORY_MOVIES);
    }

    /**
     * 得到一个在系统音乐目录下，以当前应用标识为子文件夹名的目录(如果目录不存在则尝试创建)。
     * <p/>
     * 不能获得这样一个目录，返回 null.
     */
    @CheckResult
    public static File getPublicMusicDir() {
        return getPublicDir(Environment.DIRECTORY_MUSIC);
    }

    /**
     * 得到一个在指定系统目录下，以当前应用标识为子文件夹名的目录(如果目录不存在则尝试创建)。
     * <p/>
     * 不能获得这样一个目录，返回 null.
     */
    @CheckResult
    public static File getPublicDir(String environmentDirectory) {
        File envDir = Environment.getExternalStoragePublicDirectory(environmentDirectory);
        if (envDir == null) {
            return null;
        }

        File appEnvDir = new File(envDir, AppContext.getContext().getPackageName());
        if (createDir(appEnvDir)) {
            return appEnvDir;
        }
        return null;
    }

    /**
     * 创建目录，如果创建成功，或者目录已经存在，返回true. 否则返回false.
     */
    public static boolean createDir(File file) {
        if (file == null) {
            return false;
        }

        if (!file.exists()) {
            file.mkdirs();
        }
        if (!file.isDirectory() || !file.exists()) {
            return false;
        }
        return true;
    }

    /**
     * 文件(文件夹)已删除或者删除成功返回true, 否则返回false
     */
    public static boolean deleteFileQuietly(File file) {
        if (file == null || !file.exists()) {
            return true;
        }
        if (file.isFile()) {
            file.delete();
            return !file.exists();
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (!deleteFileQuietly(f)) {
                        return false;
                    }
                }
            }
            file.delete();
            return !file.exists();
        }
        return false;
    }

    /**
     * 路径无效或者文件(文件夹)已删除或者删除成功返回true, 否则返回false
     */
    public static boolean deleteFileQuietly(String path) {
        if (TextUtils.isEmpty(path)) {
            return true;
        }
        return deleteFileQuietly(new File(path));
    }

    /**
     * 如果文件不存在并且此次创建成功，返回true. 否则返回false.
     */
    public static boolean createNewFileQuietly(File file) {
        if (file == null) {
            return false;
        }

        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            if (!createDir(parent)) {
                return false;
            }
        }

        if (file.exists()) {
            return false;
        }

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file.exists() && file.isFile();
    }

    /**
     * 从指定 url 获取扩展名, 不包含扩展名分隔符<code>.<code/>，如果获取失败，返回 null.
     */
    @CheckResult
    public static String getFileExtensionFromUrl(String url) {
        if (!TextUtils.isEmpty(url)) {
            int fragment = url.lastIndexOf('#');
            if (fragment > 0) {
                url = url.substring(0, fragment);
            }

            int query = url.lastIndexOf('?');
            if (query > 0) {
                url = url.substring(0, query);
            }

            int filenamePos = url.lastIndexOf('/');
            String filename =
                    0 <= filenamePos ? url.substring(filenamePos + 1) : url;

            // if the filename contains special characters, we don't
            // consider it valid for our matching purposes:
            if (!filename.isEmpty() &&
                    Pattern.matches("[a-zA-Z_0-9\\.\\-\\(\\)\\%]+", filename)) {
                int dotPos = filename.lastIndexOf('.');
                if (0 <= dotPos) {
                    return filename.substring(dotPos + 1);
                }
            }
        }

        return null;
    }

    /**
     * 从指定 url 获取文件名，包含扩展名, 如果获取失败，返回 null.
     */
    @CheckResult
    public static String getFilenameFromUrl(String url) {
        if (!TextUtils.isEmpty(url)) {
            int fragment = url.lastIndexOf('#');
            if (fragment > 0) {
                url = url.substring(0, fragment);
            }

            int query = url.lastIndexOf('?');
            if (query > 0) {
                url = url.substring(0, query);
            }

            int filenamePos = url.lastIndexOf('/');
            String filename =
                    0 <= filenamePos ? url.substring(filenamePos + 1) : url;
            return filename;
        }

        return null;
    }

}
