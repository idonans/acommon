package com.idonans.acommon.ext.mvp;

import android.support.annotation.CallSuper;

import com.idonans.acommon.lang.Available;
import com.idonans.acommon.lang.TaskQueue;
import com.idonans.acommon.lang.Threads;
import com.idonans.acommon.util.AvailableUtil;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by idonans on 2016/7/21.
 */
public abstract class CommonPresenter<V extends CommonView> implements Available, Closeable {

    private final TaskQueue mQueue = new TaskQueue(1);
    private V mView;
    private boolean mInit;

    public CommonPresenter(V view) {
        mView = view;
        enqueueRunnable(new Runnable() {
            @Override
            public void run() {
                onInitBackground();
                mInit = true;
            }
        });
    }

    /**
     * 如果当前 presenter 未完成初始化，则会在初始化完成之后执行
     */
    public void runAfterInit(final boolean runOnUi, final Runnable runnable) {
        if (!AvailableUtil.isAvailable(runnable)) {
            return;
        }

        if (mInit) {
            if (runOnUi) {
                Threads.runOnUi(runnable);
            } else {
                enqueueRunnable(runnable);
            }
        } else {
            enqueueRunnable(new Runnable() {
                @Override
                public void run() {
                    // assert mInit;
                    if (!AvailableUtil.isAvailable(runnable)) {
                        return;
                    }

                    if (runOnUi) {
                        Threads.runOnUi(runnable);
                    } else {
                        runnable.run();
                    }
                }
            });
        }
    }

    private void enqueueRunnable(final Runnable runnable) {
        mQueue.enqueue(new Runnable() {
            @Override
            public void run() {
                if (CommonPresenter.this.isAvailable() && AvailableUtil.isAvailable(runnable)) {
                    runnable.run();
                }
            }
        });
    }

    @CallSuper
    protected void onInitBackground() {
    }

    /**
     * if is not available, will return null.
     */
    public V getView() {
        return isAvailable() ? mView : null;
    }

    @Override
    public boolean isAvailable() {
        return AvailableUtil.isAvailable(mView);
    }

    @Override
    public void close() throws IOException {
        mView = null;
    }

}
