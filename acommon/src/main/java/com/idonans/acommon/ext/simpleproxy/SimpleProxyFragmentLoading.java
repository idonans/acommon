package com.idonans.acommon.ext.simpleproxy;

import android.os.Bundle;
import android.support.annotation.AnyThread;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idonans.acommon.R;
import com.idonans.acommon.app.CommonFragment;
import com.idonans.acommon.lang.Threads;
import com.idonans.acommon.util.ViewUtil;

/**
 * Created by idonans on 2016/11/21.
 */

public abstract class SimpleProxyFragmentLoading<DATA> extends CommonFragment {

    @AnyThread
    protected final void notifyLoadingFinished() {
        Threads.postUi(new Runnable() {
            @Override
            public void run() {
                ((SimpleProxyFragment) getParentFragment()).notifyLoadingFinished();
            }
        });
    }

    @AnyThread
    protected final DATA getData() {
        return (DATA) ((SimpleProxyFragment) getParentFragment()).getData();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startDataLoading(getData());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.acommon_simple_proxy_fragment_loading, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ContentLoadingProgressBar loadingProgressBar = ViewUtil.findViewByID(view, R.id.content_loading_progress_bar);
        if (loadingProgressBar != null) {
            loadingProgressBar.show();
        }
    }

    @UiThread
    protected abstract void startDataLoading(DATA data);

}
