package com.idonans.acommon.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.WindowInsets;
import android.widget.FrameLayout;

import com.idonans.acommon.R;

/**
 * Created by idonans on 2016/7/21.
 */
public class AutoFitSystemWindowLayout extends FrameLayout {

    public AutoFitSystemWindowLayout(Context context) {
        super(context);
    }

    public AutoFitSystemWindowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AutoFitSystemWindowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AutoFitSystemWindowLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        if (isInEditMode()) {
            return;
        }

        if (getId() != NO_ID && getId() != R.id.acommon_auto_fit_system_window_content) {
            throw new IllegalArgumentException("id should set with R.id.acommon_auto_fit_system_window_content");
        }
        setId(R.id.acommon_auto_fit_system_window_content);
        setFitsSystemWindows(true);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected final boolean fitSystemWindows(Rect insets) {
        insets.set(0, 0, 0, insets.bottom);
        return super.fitSystemWindows(insets);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    public final WindowInsets onApplyWindowInsets(WindowInsets insets) {
        insets = insets.replaceSystemWindowInsets(0, 0, 0, insets.getSystemWindowInsetBottom());
        return super.onApplyWindowInsets(insets);
    }

}
