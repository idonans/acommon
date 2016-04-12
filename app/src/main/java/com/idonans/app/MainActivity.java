package com.idonans.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.idonans.acommon.data.ProcessManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView text = (TextView) findViewById(R.id.text);
        text.setText("Process id:" + ProcessManager.getInstance().getProcessId() + "\nProcess name:" + ProcessManager.getInstance().getProcessName());
    }

}
