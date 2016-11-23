package com.idonans.acommon.demo.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idonans.acommon.demo.R;
import com.idonans.acommon.ext.simpleproxy.SimpleProxyFragmentContent;

/**
 * Created by idonans on 2016/11/21.
 */

public class SplashFragmentContent extends SimpleProxyFragmentContent<SplashData> {

    public static SplashFragmentContent newInstance() {
        Bundle args = new Bundle();
        SplashFragmentContent fragment = new SplashFragmentContent();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.splash_content, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SplashData data = getData();
    }

}
