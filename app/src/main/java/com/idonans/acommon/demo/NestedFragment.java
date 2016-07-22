package com.idonans.acommon.demo;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.idonans.acommon.app.CommonFragment;
import com.idonans.acommon.lang.CommonLog;
import com.idonans.acommon.lang.SoftKeyboardObserver;
import com.idonans.acommon.util.ViewUtil;

/**
 * Created by idonans on 16-4-25.
 */
public class NestedFragment extends CommonFragment implements SoftKeyboardObserver.SoftKeyboardListener {

    private static final String TAG = "NestedFragment";
    private EditText mEditText;
    private SoftKeyboardObserver mSoftKeyboardObserver;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSoftKeyboardObserver = new SoftKeyboardObserver(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nested, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEditText = ViewUtil.findViewByID(view, R.id.nested_edit_text);

        View hideStatusBar = ViewUtil.findViewByID(view, R.id.hide_status_bar);
        hideStatusBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideStatusBar(getActivity().getWindow());
            }
        });
        View showStatusBar = ViewUtil.findViewByID(view, R.id.show_status_bar);
        showStatusBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStatusBar(getActivity().getWindow());
            }
        });
        View transparentStatusBar = ViewUtil.findViewByID(view, R.id.transparent_status_bar);
        transparentStatusBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStatusBarTransparent(getActivity().getWindow());
            }
        });
    }

    private void hideStatusBar(Window window) {
        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    private void showStatusBar(Window window) {
        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    public void setStatusBarTransparent(Window window) {
        if (Build.VERSION.SDK_INT >= 19) {
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        if (Build.VERSION.SDK_INT >= 21) {
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        window.getDecorView().requestLayout();
    }

    @Override
    public void onResume() {
        super.onResume();
        mSoftKeyboardObserver.register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mSoftKeyboardObserver.unregister();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mEditText = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSoftKeyboardObserver = null;
    }

    @Override
    public void onSoftKeyboardOpen() {
        CommonLog.d(TAG + " onSoftKeyboardOpen");
        if (isAvailable()) {
            if (mEditText != null) {
                mEditText.setText("观察到软键盘打开");
            }
        }
    }

    @Override
    public void onSoftKeyboardClose() {
        CommonLog.d(TAG + " onSoftKeyboardClose");
        if (isAvailable()) {
            if (mEditText != null) {
                mEditText.setText("观察到软键盘关闭");
            }
        }
    }

}
