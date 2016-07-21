package com.idonans.acommon.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.WindowInsets;
import android.widget.FrameLayout;

/**
 * Created by idonans on 2016/7/21.
 */
public class NoneFitSystemWindowFrameLayout extends FrameLayout {

    public NoneFitSystemWindowFrameLayout(Context context) {
        super(context);
    }

    public NoneFitSystemWindowFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoneFitSystemWindowFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NoneFitSystemWindowFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected final boolean fitSystemWindows(Rect insets) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // Intentionally do not modify the bottom inset. For some reason,
            // if the bottom inset is modified, window resizing stops working.
            insets.left = 0;
            insets.top = 0;
            insets.right = 0;
        }

        return super.fitSystemWindows(insets);
    }

    @Override
    public final WindowInsets onApplyWindowInsets(WindowInsets insets) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            insets = insets.replaceSystemWindowInsets(0, 0, 0, insets.getSystemWindowInsetBottom());
        }

        return super.onApplyWindowInsets(insets);
    }

}
