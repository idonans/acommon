package com.idonans.acommon.demo.modules.startnavigation;

import android.os.Bundle;
import android.widget.ImageView;

import com.idonans.acommon.demo.R;
import com.idonans.acommon.ext.simpleproxy.SimpleProxy;
import com.idonans.acommon.ext.simpleproxy.SimpleProxyFragment;

/**
 * Created by idonans on 2016/12/7.
 */

public class StartNavigationViewImpl extends SimpleProxyFragment implements StartNavigationView {

    public static StartNavigationViewImpl newInstance() {
        Bundle args = new Bundle();
        StartNavigationViewImpl fragment = new StartNavigationViewImpl();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected SimpleProxy onCreateSimpleProxy() {
        return new StartNavigationProxy(this);
    }

    @Override
    public void showContent() {
        super.showContent();

        ImageView imageView = new ImageView(getActivity());
        imageView.setImageResource(R.mipmap.ic_launcher);
        getContentView().addView(imageView);
    }

}
