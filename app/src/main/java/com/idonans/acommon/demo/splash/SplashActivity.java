package com.idonans.acommon.demo.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.idonans.acommon.demo.R;
import com.idonans.acommon.demo.app.BaseActivity;
import com.idonans.acommon.util.SystemUtil;

/**
 * Created by idonans on 2016/11/21.
 */

public class SplashActivity extends BaseActivity {

    public static Intent startIntent(Context context) {
        Intent starter = new Intent(context, SplashActivity.class);
        starter.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        return starter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isAvailable()) {
            return;
        }

        SystemUtil.setFullscreenWithSystemUi(getWindow().getDecorView());
        setContentView(R.layout.splash_activity);
    }

}
