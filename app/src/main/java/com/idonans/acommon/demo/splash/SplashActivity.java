package com.idonans.acommon.demo.splash;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.idonans.acommon.demo.R;
import com.idonans.acommon.demo.app.BaseActivity;
import com.idonans.acommon.lang.SoftKeyboardObserver;
import com.idonans.acommon.util.SystemUtil;
import com.idonans.acommon.util.ViewUtil;

/**
 * Created by idonans on 2016/11/21.
 */

public class SplashActivity extends BaseActivity implements SoftKeyboardObserver.SoftKeyboardListener {

    private SoftKeyboardObserver mSoftKeyboardObserver;
    private TextView mMessageShow;
    private View mSetFullscreen, mUnsetFullscreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isAvailable()) {
            return;
        }

        setContentView(R.layout.splash_activity);

        mSoftKeyboardObserver = new SoftKeyboardObserver(this);
        mMessageShow = ViewUtil.findViewByID(this, R.id.message_show);
        mSetFullscreen = ViewUtil.findViewByID(this, R.id.set_fullscreen);
        mSetFullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SystemUtil.setFullscreenWithSystemUi(v);
            }
        });
        mUnsetFullscreen = ViewUtil.findViewByID(this, R.id.unset_fullscreen);
        mUnsetFullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SystemUtil.unsetFullscreenWithSystemUi(v);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mSoftKeyboardObserver != null) {
            mSoftKeyboardObserver.register(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mSoftKeyboardObserver != null) {
            mSoftKeyboardObserver.unregister();
        }
    }

    @Override
    public void onSoftKeyboardOpen() {
        if (mMessageShow != null) {
            mMessageShow.setText("soft keyboard is open");
        }
    }

    @Override
    public void onSoftKeyboardClose() {
        if (mMessageShow != null) {
            mMessageShow.setText("soft keyboard is closed");
        }
    }

}
