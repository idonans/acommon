package com.idonans.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.idonans.acommon.app.CommonFragment;
import com.idonans.acommon.lang.SoftKeyboardObserver;
import com.idonans.acommon.util.SystemUtil;
import com.idonans.acommon.util.ViewUtil;

/**
 * Created by idonans on 16-4-25.
 */
public class CustomKeyboardFragment extends CommonFragment implements SoftKeyboardObserver.SoftKeyboardListener {

    private static final String TAG = "CustomKeyboardFragment";

    private SoftKeyboardObserver mSoftKeyboardObserver;

    private TextView mEmotionKeyboardSwitch;
    private TextView mInputSubmit;
    private EditText mInputEditText;
    private View mEmotionPanel;
    private TextView mShowInputContent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSoftKeyboardObserver = new SoftKeyboardObserver(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_custom_keyboard, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEmotionKeyboardSwitch = ViewUtil.findViewByID(view, R.id.emotion_keyboard_switch);
        mInputSubmit = ViewUtil.findViewByID(view, R.id.input_submit);
        mInputEditText = ViewUtil.findViewByID(view, R.id.input_edit_text);
        mEmotionPanel = ViewUtil.findViewByID(view, R.id.emotion_panel);

        mShowInputContent = ViewUtil.findViewByID(view, R.id.show_input_content);
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
    public void onSoftKeyboardOpen() {
        if (!isAvailable()) {
            return;
        }
        if (mEmotionKeyboardSwitch != null) {
            mEmotionKeyboardSwitch.setText("表情输入");
            mEmotionKeyboardSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // close soft keyboard
                    if (mInputEditText != null) {
                        SystemUtil.hideSoftKeyboard(mInputEditText);
                    }
                }
            });
        }

        if (mEmotionPanel != null) {
            mEmotionPanel.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSoftKeyboardClose() {
        if (!isAvailable()) {
            return;
        }
        if (mEmotionKeyboardSwitch != null) {
            mEmotionKeyboardSwitch.setText("键盘输入");
            mEmotionKeyboardSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // show soft keyboard
                    if (mInputEditText != null) {
                        SystemUtil.showSoftKeyboard(mInputEditText);
                    }
                }
            });
        }

        if (mEmotionPanel != null) {
            mEmotionPanel.setVisibility(View.VISIBLE);
        }
    }

}
