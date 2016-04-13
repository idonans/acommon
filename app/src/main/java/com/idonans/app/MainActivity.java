package com.idonans.app;

import android.content.ContextWrapper;
import android.os.Bundle;
import android.widget.TextView;

import com.idonans.acommon.app.CommonActivity;
import com.idonans.acommon.data.ProcessManager;
import com.idonans.acommon.util.DimenUtil;

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
        text.setText(builder);
    }

}
