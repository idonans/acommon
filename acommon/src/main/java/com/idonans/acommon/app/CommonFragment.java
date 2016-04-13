package com.idonans.acommon.app;

import android.support.v4.app.Fragment;

/**
 * Created by idonans on 16-4-13.
 */
public class CommonFragment extends Fragment {

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

    public boolean isPaused() {
        return mPaused;
    }

}
