package com.idonans.acommon.demo.modules.splash;

import android.os.Bundle;

import com.idonans.acommon.demo.R;
import com.idonans.acommon.ext.simpleproxy.SimpleProxy;
import com.idonans.acommon.ext.simpleproxy.SimpleProxyFragment;

/**
 * Created by idonans on 2016/12/7.
 */

public class SplashViewImpl extends SimpleProxyFragment implements SplashView {

    public static SplashViewImpl newInstance() {
        Bundle args = new Bundle();
        SplashViewImpl fragment = new SplashViewImpl();
        fragment.setArguments(args);
        fragment.setStatusBarPadding(true);
        return fragment;
    }

    @Override
    protected SimpleProxy onCreateSimpleProxy() {
        return new SplashProxy(this);
    }

    @Override
    public void showContent() {
        super.showContent();
        getLayoutInflater(null).inflate(R.layout.splash_content, getContentView(), true);
    }

}
