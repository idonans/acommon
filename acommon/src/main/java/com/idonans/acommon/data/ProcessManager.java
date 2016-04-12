package com.idonans.acommon.data;

import android.app.ActivityManager;
import android.content.Context;

import com.idonans.acommon.AppContext;

import java.util.List;

/**
 * 记录进程信息，在 app 中可能存在多个进程，在处理如缓存路径时进程之间的应当不同，否则可能出现读写冲突。
 * Created by idonans on 16-4-12.
 */
public class ProcessManager {

    private static class InstanceHolder {

        private static final ProcessManager sInstance = new ProcessManager();

    }

    public static ProcessManager getInstance() {
        return InstanceHolder.sInstance;
    }

    private int mProcessId;
    private String mProcessName;

    private ProcessManager() {
        mProcessId = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) AppContext.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processes = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo processInfo : processes) {
            if (processInfo.pid == mProcessId) {
                mProcessName = processInfo.processName;
                break;
            }
        }

    }

    public int getProcessId() {
        return mProcessId;
    }

    public String getProcessName() {
        return mProcessName;
    }

}
