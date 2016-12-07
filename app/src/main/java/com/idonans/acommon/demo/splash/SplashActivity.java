package com.idonans.acommon.demo.splash;

import com.idonans.acommon.demo.modules.splash.SplashViewImpl;
import com.idonans.acommon.ext.simpleproxy.SimpleProxyActivity;
import com.idonans.acommon.ext.simpleproxy.SimpleProxyFragment;

/**
 * Created by idonans on 2016/11/21.
 */

public class SplashActivity extends SimpleProxyActivity {

    @Override
    protected SimpleProxyFragment createSimpleProxyFragment() {
        return SplashViewImpl.newInstance();
    }

}
