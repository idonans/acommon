package com.idonans.acommon.demo.splash;

import android.os.Bundle;

import com.idonans.acommon.demo.R;
import com.idonans.acommon.demo.app.BaseActivity;

/**
 * Created by idonans on 2016/11/21.
 */

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isAvailable()) {
            return;
        }

        setContentView(R.layout.splash_activity);
    }

}
