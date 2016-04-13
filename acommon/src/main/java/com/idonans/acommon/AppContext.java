package com.idonans.acommon;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * 在程序启动式设置 Context, 通常在 Application#onCreate, ContentProvider#onCreate
 * Created by idonans on 16-4-12.
 */
public class AppContext {

    private static Context sContext;

    @NonNull
    public static Context getContext() {
        return sContext;
    }

    public static void setContext(Context context) {
        sContext = context.getApplicationContext();
    }

}
