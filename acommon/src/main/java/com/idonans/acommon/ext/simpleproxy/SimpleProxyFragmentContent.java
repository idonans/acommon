package com.idonans.acommon.ext.simpleproxy;

import android.support.annotation.AnyThread;

import com.idonans.acommon.app.CommonFragment;

/**
 * Created by idonans on 2016/11/21.
 */

public class SimpleProxyFragmentContent<DATA> extends CommonFragment {

    @AnyThread
    protected final DATA getData() {
        return (DATA) ((SimpleProxyFragment) getParentFragment()).getData();
    }

}
