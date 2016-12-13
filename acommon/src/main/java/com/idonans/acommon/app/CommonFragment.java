package com.idonans.acommon.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.idonans.acommon.App;
import com.idonans.acommon.lang.Available;
import com.idonans.acommon.lang.CommonLog;

/**
 * 基类 Fragment
 * Created by idonans on 16-4-13.
 */
public class CommonFragment extends Fragment implements Available {

    private boolean mAvailable;

    private final String DEBUG_TAG = getClass().getName();

    private boolean isDebug() {
        return App.getBuildConfigAdapter().isDebug();
    }

    @Override
    public void onStart() {
        if (isDebug()) {
            CommonLog.d(DEBUG_TAG + " onStart");
        }

        super.onStart();
    }

    @Override
    public void onPause() {
        if (isDebug()) {
            CommonLog.d(DEBUG_TAG + " onPause");
        }

        super.onPause();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (isDebug()) {
            CommonLog.d(DEBUG_TAG + " onCreate");
        }

        super.onCreate(savedInstanceState);
        mAvailable = true;
    }

    @Override
    public void onDestroy() {
        if (isDebug()) {
            CommonLog.d(DEBUG_TAG + " onDestroy");
        }

        super.onDestroy();
        mAvailable = false;
    }

    @Override
    public boolean isAvailable() {
        return mAvailable;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onStop() {
        if (isDebug()) {
            CommonLog.d(DEBUG_TAG + " onStop");
        }

        super.onStop();
    }

    @Override
    public void onResume() {
        if (isDebug()) {
            CommonLog.d(DEBUG_TAG + " onResume");
        }

        super.onResume();
    }

}
