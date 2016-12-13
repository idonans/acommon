package com.idonans.acommon.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.idonans.acommon.App;
import com.idonans.acommon.lang.Available;
import com.idonans.acommon.lang.CommonLog;
import com.idonans.acommon.util.SystemUtil;

/**
 * 基类 Activity
 * Created by idonans on 16-4-13.
 */
public class CommonActivity extends AppCompatActivity implements Available {

    private boolean mAvailable;
    private boolean mPaused;
    private boolean mTransparentStatusBar = true;

    private final String DEBUG_TAG = getClass().getName();

    private boolean isDebug() {
        return App.getBuildConfigAdapter().isDebug();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (isDebug()) {
            CommonLog.d(DEBUG_TAG + " onCreate");
        }

        if (mTransparentStatusBar) {
            SystemUtil.setStatusBarTransparent(getWindow());
        }
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
        showContent();
    }

    /**
     * default is true, u can change to false but must call before onCreate()
     * <pre>
     *     protected void onCreate() {
     *         setTransparentStatusBar(false);
     *         super.onCreate();
     *     }
     *
     *     protected void showContent() {
     *         setContentView();
     *         Button signIn = ViewUtil.findViewByID();
     *     }
     * </pre>
     *
     * @param transparentStatusBar
     */
    protected void setTransparentStatusBar(boolean transparentStatusBar) {
        mTransparentStatusBar = transparentStatusBar;
    }

    /**
     * override this to setContentView
     */
    protected void showContent() {
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (isDebug()) {
            CommonLog.d(DEBUG_TAG + " onNewIntent");
        }

        super.onNewIntent(intent);
    }

    @Override
    protected void onRestart() {
        if (isDebug()) {
            CommonLog.d(DEBUG_TAG + " onStart");
        }

        super.onRestart();
    }

    @Override
    protected void onStart() {
        if (isDebug()) {
            CommonLog.d(DEBUG_TAG + " onStart");
        }

        super.onStart();
    }

    @Override
    protected void onStop() {
        if (isDebug()) {
            CommonLog.d(DEBUG_TAG + " onStop");
        }
        super.onStop();
    }

    @Override
    protected void onPause() {
        if (isDebug()) {
            CommonLog.d(DEBUG_TAG + " onPause");
        }

        super.onPause();
        mPaused = true;
    }

    @Override
    protected void onResume() {
        if (isDebug()) {
            CommonLog.d(DEBUG_TAG + " onResume");
        }

        super.onResume();
        mPaused = false;
    }

    public boolean isPaused() {
        return mPaused;
    }

    @Override
    public void onBackPressed() {
        if (!isPaused() && isAvailable()) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        if (isDebug()) {
            CommonLog.d(DEBUG_TAG + " onDestroy");
        }

        super.onDestroy();
        mAvailable = false;
    }

    @Override
    public boolean isAvailable() {
        return mAvailable && !isFinishing();
    }

}