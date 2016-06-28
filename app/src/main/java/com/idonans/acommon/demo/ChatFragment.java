package com.idonans.acommon.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.idonans.acommon.app.CommonFragment;
import com.idonans.acommon.lang.CommonLog;
import com.idonans.acommon.lang.SoftKeyboardObserver;
import com.idonans.acommon.util.ViewUtil;

/**
 * Created by idonans on 16-4-25.
 */
public class ChatFragment extends CommonFragment implements SoftKeyboardObserver.SoftKeyboardListener {

    private static final String TAG = "ChatFragment";
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
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEditText = ViewUtil.findViewByID(view, R.id.edit_text);
        addNestedFragment();
    }

    private void addNestedFragment() {
        FragmentManager fragmentManager = getChildFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.nested_fragment_contain);
        if (fragment == null) {
            fragment = Fragment.instantiate(getContext(), NestedFragment.class.getName());
            fragmentManager.beginTransaction().add(R.id.nested_fragment_contain, fragment).commit();
        }
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
