package com.idonans.acommon.ext.simpleproxy;

import com.idonans.acommon.ext.viewproxy.ViewProxy;

/**
 * Created by idonans on 2016/12/7.
 */

public class SimpleProxy<VIEW extends SimpleProxyView> extends ViewProxy<VIEW> {

    public SimpleProxy(VIEW view) {
        super(view);
    }

    @Override
    protected void onLoading() {
        VIEW view = getView();
        if (view == null) {
            return;
        }

        view.showLoading();
    }

    @Override
    protected void onStart() {
        VIEW view = getView();
        if (view == null) {
            return;
        }

        view.showContent();
    }

}
