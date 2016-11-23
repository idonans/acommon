package com.idonans.acommon.demo.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idonans.acommon.ext.simpleproxy.SimpleProxyFragmentLoading;

/**
 * Created by idonans on 2016/11/21.
 */

public class SplashFragmentLoading extends SimpleProxyFragmentLoading<SplashData> {

    public static SplashFragmentLoading newInstance() {
        Bundle args = new Bundle();
        SplashFragmentLoading fragment = new SplashFragmentLoading();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void startDataLoading(SplashData splashData) {
        notifyLoadingFinished();
    }

}
