package com.idonans.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.idonans.acommon.app.CommonActivity;

/**
 * Created by idonans on 16-4-25.
 */
public class ContainActivity extends CommonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contain);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.content);
        if (fragment == null) {
            fragment = Fragment.instantiate(this, ChatFragment.class.getName());
            fragmentManager.beginTransaction().add(R.id.content, fragment).commit();
        }
    }

}
