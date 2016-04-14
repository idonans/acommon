package com.idonans.app;

import android.content.ContextWrapper;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.idonans.acommon.app.CommonActivity;
import com.idonans.acommon.data.AppIDManager;
import com.idonans.acommon.data.ProcessManager;
import com.idonans.acommon.data.StorageManager;
import com.idonans.acommon.util.DimenUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends CommonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView text = (TextView) findViewById(R.id.text);

        StringBuilder builder = new StringBuilder();
        builder.append("Process id:").append(ProcessManager.getInstance().getProcessId()).append("\n");
        builder.append("Process name:").append(ProcessManager.getInstance().getProcessName()).append("\n");
        builder.append("Process tag:").append(ProcessManager.getInstance().getProcessTag()).append("\n");
        builder.append("Process is main:").append(ProcessManager.getInstance().isMainProcess()).append("\n");
        builder.append("dp2px 0.2dp->").append(DimenUtil.dp2px(0.2f)).append("\n");
        builder.append("sp2px 0.2sp->").append(DimenUtil.sp2px(0.2f)).append("\n");
        builder.append("text context info:").append(text.getContext().getClass()).append("\n");
        builder.append("text context base info:").append(((ContextWrapper) text.getContext()).getBaseContext().getClass()).append("\n");
        builder.append("app first run time:").append(getAppFirstRunTime()).append("\n");
        builder.append("app last run time:").append(getAppLastRunTime()).append("\n");
        builder.append("app id:").append(AppIDManager.getInstance().getAppID()).append("\n");
        text.setText(builder);

        View printDBContent = findViewById(R.id.print_db_content);
        printDBContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StorageManager.getInstance().printCacheContent();
                StorageManager.getInstance().printSettingContent();
            }
        });
    }

    private String getAppFirstRunTime() {
        String key = "first run time";
        String time = StorageManager.getInstance().getSetting(key);
        if (TextUtils.isEmpty(time)) {
            time = getTimeNow();
            StorageManager.getInstance().setSetting(key, time);
        }
        return time;
    }

    private String getAppLastRunTime() {
        String key = "last run time";
        String time = StorageManager.getInstance().getSetting(key);
        StorageManager.getInstance().setSetting(key, getTimeNow());
        return time;
    }

    private String getTimeNow() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

}
