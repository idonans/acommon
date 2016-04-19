package com.idonans.acommon.lang;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;

import com.idonans.acommon.util.SystemUtil;

/**
 * 辅助观察软键盘的行为
 * Created by idonans on 16-4-19.
 */
public class SoftKeyboardObserver implements ViewTreeObserver.OnGlobalLayoutListener {

    private static final String TAG = "SoftKeyboardObserver";
    private final SoftKeyboardListener mListener;

    private final int METHOD_OPEN = 1;
    private final int METHOD_CLOSE = 2;
    // 避免同一个回调被连续调用两次，比如避免连续调用两次软键盘打开。
    private int mLastCallMethod;

    private Activity mActivity;
    private View mContentView;

    public SoftKeyboardObserver(@Nullable SoftKeyboardListener listener) {
        mListener = new SoftKeyboardListenerPoster(listener);
    }

    public void register(@NonNull Activity activity) {
        if (mActivity == activity) {
            CommonLog.e(TAG + " already register on " + activity.getClass().getName() + "@" + activity.hashCode());
            return;
        }

        if (mActivity != null) {
            throw new IllegalAccessError("already register on another " + mActivity.getClass().getName() + "@" + mActivity.hashCode());
        }

        checkWindowSoftInputMode(activity);

        mActivity = activity;
        mContentView = mActivity.findViewById(Window.ID_ANDROID_CONTENT);
        mContentView.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    private void checkWindowSoftInputMode(Activity activity) {
        if ((activity.getWindow().getAttributes().softInputMode & WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE) == 0) {
            throw new IllegalArgumentException("softInputMode is not adjustResize " + activity.getClass().getName() + "@" + activity.hashCode());
        }
    }

    public void unregister() {
        if (mActivity == null) {
            CommonLog.e(TAG + " activity not register or already unregister");
            return;
        }

        if (Build.VERSION.SDK_INT >= 16) {
            mContentView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        } else {
            mContentView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        }
        mActivity = null;
        mContentView = null;
    }

    private void onSoftKeyboardOpen() {
        if (mLastCallMethod == METHOD_OPEN) {
            return;
        }
        mLastCallMethod = METHOD_OPEN;
        mListener.onSoftKeyboardOpen();
    }

    private void onSoftKeyboardClose() {
        if (mLastCallMethod == METHOD_CLOSE) {
            return;
        }
        mLastCallMethod = METHOD_CLOSE;
        mListener.onSoftKeyboardClose();
    }

    @Override
    public void onGlobalLayout() {
        if (SystemUtil.isSoftKeyboardShown(mActivity)) {
            onSoftKeyboardOpen();
        } else {
            onSoftKeyboardClose();
        }
    }

    public interface SoftKeyboardListener {
        void onSoftKeyboardOpen();

        void onSoftKeyboardClose();
    }

    private class SoftKeyboardListenerPoster implements SoftKeyboardListener {
        private final SoftKeyboardListener mListener;

        private SoftKeyboardListenerPoster(SoftKeyboardListener listener) {
            mListener = listener;
        }

        @Override
        public void onSoftKeyboardOpen() {
            if (mListener != null) {
                Threads.postUi(new Runnable() {
                    @Override
                    public void run() {
                        mListener.onSoftKeyboardOpen();
                    }
                });
            }
        }

        @Override
        public void onSoftKeyboardClose() {
            if (mListener != null) {
                Threads.postUi(new Runnable() {
                    @Override
                    public void run() {
                        mListener.onSoftKeyboardClose();
                    }
                });
            }
        }

    }

}
