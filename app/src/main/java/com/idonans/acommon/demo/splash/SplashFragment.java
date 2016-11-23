package com.idonans.acommon.demo.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.idonans.acommon.ext.simpleproxy.SimpleProxyFragment;
import com.idonans.acommon.ext.simpleproxy.SimpleProxyFragmentContent;
import com.idonans.acommon.ext.simpleproxy.SimpleProxyFragmentLoading;

/**
 * Created by idonans on 2016/11/21.
 */

public class SplashFragment extends SimpleProxyFragment<SplashData> {

    public static SplashFragment newInstance() {
        Bundle args = new Bundle();
        SplashFragment fragment = new SplashFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected boolean needShowLoading() {
        return false;
    }

    @Override
    protected SplashData onCreateData(@Nullable Bundle savedInstanceState) {
        return new SplashData();
    }

    @Override
    protected void onSaveData(Bundle outState) {
    }

    @Override
    protected SimpleProxyFragmentLoading<SplashData> createLoadingFragment() {
        return SplashFragmentLoading.newInstance();
    }

    @Override
    protected SimpleProxyFragmentContent<SplashData> createContentFragment() {
        return SplashFragmentContent.newInstance();
    }

}
