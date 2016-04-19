package com.idonans.acommon.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.idonans.acommon.lang.Available;
import com.idonans.acommon.lang.CommonLog;

/**
 * 基类 Activity
 * Created by idonans on 16-4-13.
 */
public class CommonActivity extends AppCompatActivity implements Available {

    private boolean mAvailable;
    private boolean mPaused;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        {
            // 修复根 Activity 重复启动的 bug
            if (!isTaskRoot()) {
                Intent intent = getIntent();
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(intent.getAction())) {
                    CommonLog.e("close this launcher instance " + getClass().getName() + "@" + hashCode());
                    finish();
                    return;
                }
            }
        }

        mAvailable = true;
    }

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAvailable = false;
    }

    @Override
    public boolean isAvailable() {
        return mAvailable && !isFinishing();
    }

}