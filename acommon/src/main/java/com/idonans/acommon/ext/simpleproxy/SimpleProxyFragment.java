package com.idonans.acommon.ext.simpleproxy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.idonans.acommon.R;
import com.idonans.acommon.app.CommonFragment;
import com.idonans.acommon.lang.CommonLog;
import com.idonans.acommon.util.IOUtil;
import com.idonans.acommon.util.ViewUtil;

/**
 * Created by idonans on 2016/11/21.
 */

public abstract class SimpleProxyFragment extends CommonFragment implements SimpleProxyView {

    private static final String EXTRA_STATUS_BAR_PADDING = "status_bar_padding";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setStatusBarPadding(boolean statusBarPadding) {
        Bundle args = getArguments();
        args.putBoolean(EXTRA_STATUS_BAR_PADDING, statusBarPadding);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container == null) {
            final String TAG = getClass().getSimpleName();
            CommonLog.d(TAG + " onCreateView but container is null. savedInstanceState:" + savedInstanceState);
        }

        if (savedInstanceState != null) {
            final String TAG = getClass().getSimpleName();
            CommonLog.d(TAG + " onCreateView restore from savedInstanceState:" + savedInstanceState + ", with container:" + container);
        }

        View view = inflater.inflate(R.layout.acommon_simple_proxy_fragment, container, false);

        boolean hasStatusBarPadding = getArguments().getBoolean(EXTRA_STATUS_BAR_PADDING, false);
        if (hasStatusBarPadding) {
            view.setPadding(0, getResources().getDimensionPixelOffset(R.dimen.acommon_status_bar_height), 0, 0);
        }

        return view;
    }

    private FrameLayout mContentView;

    public void setContentView(FrameLayout contentView) {
        mContentView = contentView;
    }

    public FrameLayout getContentView() {
        return mContentView;
    }

    private SimpleProxy mSimpleProxy;

    public SimpleProxy getSimpleProxy() {
        return mSimpleProxy;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        FrameLayout content = ViewUtil.findViewByID(view, R.id.acommon_simple_proxy_fragment_content);
        setContentView(content);

        SimpleProxy simpleProxy = onCreateSimpleProxy();
        mSimpleProxy = simpleProxy;
        mSimpleProxy.start();
    }

    protected SimpleProxy onCreateSimpleProxy() {
        return new SimpleProxy(this);
    }

    @Override
    public void showLoading() {
        FrameLayout contentView = getContentView();
        if (contentView != null) {
            contentView.removeAllViews();

            View view = getActivity().getLayoutInflater().inflate(R.layout.acommon_simple_proxy_loading, contentView, false);
            ContentLoadingProgressBar loadingView = ViewUtil.findViewByID(view, R.id.acommon_simple_proxy_loading);
            contentView.addView(view);
            loadingView.show();
        }
    }

    @Override
    public void showContent() {
        FrameLayout contentView = getContentView();
        if (contentView != null) {
            contentView.removeAllViews();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mContentView = null;
        IOUtil.closeQuietly(mSimpleProxy);
    }

}
