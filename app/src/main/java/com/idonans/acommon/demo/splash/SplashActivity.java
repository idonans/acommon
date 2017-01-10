package com.idonans.acommon.demo.splash;

import android.os.Bundle;
import android.widget.TextView;

import com.idonans.acommon.demo.R;
import com.idonans.acommon.demo.app.BaseActivity;
import com.idonans.acommon.util.SystemUtil;
import com.idonans.acommon.util.ViewUtil;

/**
 * Created by idonans on 2016/11/21.
 */

public class SplashActivity extends BaseActivity {

    private TextView mMessageShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isAvailable()) {
            return;
        }

        setContentView(R.layout.splash_activity);

        mMessageShow = ViewUtil.findViewByID(this, R.id.message_show);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isAvailable()) {
            return;
        }

        mMessageShow.postOnAnimation(new Runnable() {
            @Override
            public void run() {
                syncSoftKeyboardStatus();
            }
        });
    }

    private void syncSoftKeyboardStatus() {
        if (SystemUtil.isSoftKeyboardShown(this)) {
            mMessageShow.setText("soft keyboard is shown");
        } else {
            mMessageShow.setText("soft keyboard is hidden");
        }
    }

}
