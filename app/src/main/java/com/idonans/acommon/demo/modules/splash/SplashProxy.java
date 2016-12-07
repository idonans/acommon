package com.idonans.acommon.demo.modules.splash;

import com.idonans.acommon.ext.simpleproxy.SimpleProxy;
import com.idonans.acommon.lang.Threads;

/**
 * Created by idonans on 2016/12/7.
 */

public class SplashProxy extends SimpleProxy<SplashView> {

    public SplashProxy(SplashView view) {
        super(view);
    }

    @Override
    protected void onInitBackground() {
        super.onInitBackground();
        Threads.sleepQuietly(5000);
    }

}
