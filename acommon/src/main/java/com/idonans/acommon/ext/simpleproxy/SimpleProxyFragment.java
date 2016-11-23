package com.idonans.acommon.ext.simpleproxy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idonans.acommon.R;
import com.idonans.acommon.app.CommonFragment;

/**
 * Created by idonans on 2016/11/21.
 */

public abstract class SimpleProxyFragment<DATA> extends CommonFragment {

    private DATA mData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(null);
        mData = onCreateData(savedInstanceState);
    }

    protected abstract DATA onCreateData(@Nullable Bundle savedInstanceState);

    protected abstract void onSaveData(Bundle outState);

    protected boolean needShowLoading() {
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        onSaveData(outState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.acommon_simple_proxy_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (needShowLoading()) {
            showFragmentWithReplace(createLoadingFragment());
        } else {
            showFragmentWithReplace(createContentFragment());
        }
    }

    void showFragmentWithReplace(CommonFragment fragment) {
        if (!isAvailable()) {
            return;
        }
        getChildFragmentManager().beginTransaction().replace(R.id.acommon_simple_proxy_fragment_content, fragment).commitNowAllowingStateLoss();
    }

    void notifyLoadingFinished() {
        showFragmentWithReplace(createContentFragment());
    }

    DATA getData() {
        return mData;
    }

    protected abstract SimpleProxyFragmentLoading<DATA> createLoadingFragment();

    protected abstract SimpleProxyFragmentContent<DATA> createContentFragment();

}
