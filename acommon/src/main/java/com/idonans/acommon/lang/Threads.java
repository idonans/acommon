package com.idonans.acommon.lang;

import android.os.Handler;
import android.os.Looper;

import com.idonans.acommon.data.ProcessManager;

/**
 * 线程辅助类
 * Created by idonans on 16-4-13.
 */
public class Threads {

    private static final Handler sHandlerUi = new Handler(Looper.getMainLooper());

    /**
     * 如果当前已经处于 ui 线程，则直接执行，否则 post 到 ui 线程执行。
     */
    public static void runOnUi(Runnable runnable) {
        if (isUi()) {
            runnable.run();
        } else {
            sHandlerUi.post(runnable);
        }
    }

    /**
     * 总是将任务 post 到 ui 线程执行，即使当前已经处于 ui 线程。
     */
    public static void postUi(Runnable runnable) {
        sHandlerUi.post(runnable);
    }

    public static void postBackground(Runnable runnable) {
        ThreadPool.getInstance().post(runnable);
    }

    public static void sleepQuietly(long ms) {
        try {
            Thread.sleep(ms);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static boolean isUi() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static void mustUi() {
        if (!isUi()) {
            Exception e = new IllegalThreadStateException("此处需要是 ui 线程");
            e.printStackTrace();
            CommonLog.e(e.getLocalizedMessage());
        }
    }

    public static void mustNotUi() {
        if (isUi()) {
            Exception e = new IllegalThreadStateException("此处不允许为 ui 线程");
            e.printStackTrace();
            CommonLog.e(e.getLocalizedMessage());
        }
    }

    public static void mustMainProcess() {
        if (!ProcessManager.getInstance().isMainProcess()) {
            Exception e = new IllegalThreadStateException("此处需要是主进程");
            e.printStackTrace();
            CommonLog.e(e.getLocalizedMessage());
        }
    }

    public static void mustNotMainProcess() {
        if (ProcessManager.getInstance().isMainProcess()) {
            Exception e = new IllegalThreadStateException("此处不允许为主进程");
            e.printStackTrace();
            CommonLog.e(e.getLocalizedMessage());
        }
    }

}
