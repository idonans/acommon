package com.idonans.acommon.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.idonans.acommon.app.CommonActivity;
import com.idonans.acommon.util.SystemUtil;

/**
 * Created by idonans on 16-4-25.
 */
public class ContainActivity extends CommonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contain);

        SystemUtil.setStatusBarTransparent(getWindow());

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.content);
        if (fragment == null) {
            fragment = Fragment.instantiate(this, ChatFragment.class.getName());
            fragmentManager.beginTransaction().add(R.id.content, fragment).commit();
        }
    }

}
