package com.idonans.acommon.ext.simpleproxy;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.idonans.acommon.R;
import com.idonans.acommon.app.CommonActivity;
import com.idonans.acommon.app.CommonFragment;
import com.idonans.acommon.util.SystemUtil;

/**
 * Created by idonans on 2016/11/21.
 */

public abstract class SimpleProxyActivity extends CommonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SystemUtil.setStatusBarTransparent(getWindow());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acommon_simple_proxy_activity);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.acommon_simple_proxy_activity_content);
        if (fragment == null) {
            showFragmentWithReplace(createSimpleProxyFragment());
        }
    }

    @Override
    protected void showContent() {
        setContentView(R.layout.acommon_simple_proxy_activity);
    }

    private void showFragmentWithReplace(CommonFragment fragment) {
        if (!isAvailable()) {
            return;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.acommon_simple_proxy_activity_content, fragment).commitNowAllowingStateLoss();
    }

    protected abstract SimpleProxyFragment createSimpleProxyFragment();

}
