package com.idonans.acommon.app;

import android.support.v7.app.AppCompatActivity;

/**
 * 基类 Activity
 * Created by idonans on 16-4-13.
 */
public class CommonActivity extends AppCompatActivity {

    private boolean mPaused;

    @Override
    protected void onPause() {
        super.onPause();
        mPaused = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPaused = false;
    }

    public boolean isPaused() {
        return mPaused;
    }

    @Override
    public void onBackPressed() {
        if (!isPaused()) {
            super.onBackPressed();
        }
    }

}