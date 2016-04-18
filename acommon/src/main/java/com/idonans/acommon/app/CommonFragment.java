package com.idonans.acommon.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.idonans.acommon.lang.Available;

/**
 * 基类 Fragment
 * Created by idonans on 16-4-13.
 */
public class CommonFragment extends Fragment implements Available {

    private boolean mAvailable;
    private boolean mPaused;

    @Override
    public void onPause() {
        super.onPause();
        mPaused = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPaused = false;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAvailable = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAvailable = false;
    }

    public boolean isPaused() {
        return mPaused;
    }

    @Override
    public boolean isAvailable() {
        return mAvailable;
    }

}
