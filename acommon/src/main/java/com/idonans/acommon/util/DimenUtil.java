package com.idonans.acommon.util;

import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.idonans.acommon.AppContext;

/**
 * dp,sp to px
 * Created by idonans on 16-4-13.
 */
public class DimenUtil {

    public static int dp2px(float dp) {
        DisplayMetrics metrics = AppContext.getContext().getResources().getDisplayMetrics();
        float v = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                metrics);
        if (v > 0) {
            int iv = Math.round(v);
            if (iv == 0) {
                iv = 1;
            }
            return iv;
        }
        return 0;
    }

    public static int sp2px(float sp) {
        DisplayMetrics metrics = AppContext.getContext().getResources().getDisplayMetrics();
        float v = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                metrics);
        if (v > 0) {
            int iv = Math.round(v);
            if (iv == 0) {
                iv = 1;
            }
            return iv;
        }
        return 0;
    }

}
