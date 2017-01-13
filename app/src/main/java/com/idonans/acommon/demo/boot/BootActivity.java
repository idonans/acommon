package com.idonans.acommon.demo.boot;

import android.content.Intent;
import android.os.Bundle;

import com.idonans.acommon.demo.app.BaseActivity;
import com.idonans.acommon.demo.splash.SplashActivity;
import com.idonans.acommon.lang.Threads;

/**
 * Created by idonans on 2017/1/13.
 */

public class BootActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isAvailable()) {
            return;
        }

        Threads.sleepQuietly(1000);

        Intent intent = SplashActivity.startIntent(this);
        intent.putExtras(getIntent());
        startActivity(intent);
        finish();
    }

}
