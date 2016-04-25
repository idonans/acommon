package com.idonans.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;

import com.idonans.acommon.app.CommonActivity;
import com.idonans.acommon.lang.CommonLog;

/**
 * Created by idonans on 16-4-25.
 */
public class FragmentContainerActivity extends CommonActivity {

    private static final String TAG = "FragmentContainerActivity";
    public static final String EXTRA_FRAGMENT_NAME = "EXTRA_FRAGMENT_NAME";

    public static Intent newIntent(Context context, Class fragmentClass) {
        Intent intent = new Intent(context, FragmentContainerActivity.class);
        intent.putExtra(EXTRA_FRAGMENT_NAME, fragmentClass.getName());
        return intent;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            String fragmentName = getIntent().getStringExtra(EXTRA_FRAGMENT_NAME);
            if (TextUtils.isEmpty(fragmentName)) {
                CommonLog.e(TAG + " fragment name not found");
            } else {
                fragment = Fragment.instantiate(this, fragmentName);
                fragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
            }
        }
    }

}
