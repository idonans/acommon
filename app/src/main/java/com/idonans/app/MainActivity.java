package com.idonans.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.idonans.acommon.app.CommonActivity;
import com.idonans.acommon.app.CommonService;
import com.idonans.acommon.data.ProcessManager;
import com.idonans.acommon.data.StorageManager;
import com.idonans.acommon.util.AssetUtil;
import com.idonans.acommon.util.AvailableUtil;
import com.idonans.acommon.util.DimenUtil;
import com.idonans.acommon.util.FileUtil;
import com.idonans.acommon.util.HumanUtil;
import com.idonans.acommon.util.MD5Util;
import com.idonans.acommon.util.RegexUtil;
import com.idonans.acommon.util.SystemUtil;

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
        builder.append("app first run time:").append(getAppFirstRunTime()).append("\n");
        builder.append("app last run time:").append(getAppLastRunTime()).append("\n");
        builder.append("app id:").append(SystemUtil.getAppID()).append("\n");
        builder.append("max heap size:").append(SystemUtil.getMaxHeapSize()).append("\n");
        builder.append("md5 null string:").append(MD5Util.md5(null)).append("\n");
        builder.append("md5 empty string:").append(MD5Util.md5("")).append("\n");
        builder.append("md5 string 123:").append(MD5Util.md5("123")).append("\n");
        builder.append("human size 9803 byte:").append(HumanUtil.getHumanSizeFromByte(9803)).append("\n");
        builder.append("human size -9803 byte:").append(HumanUtil.getHumanSizeFromByte(-9803)).append("\n");
        builder.append("phone number regex 13010101011:").append(RegexUtil.isPhoneNumber("13010101011")).append("\n");
        builder.append("read assets:").append(readAsset()).append("\n");
        builder.append("get filename from url:").append(FileUtil.getFilenameFromUrl("http://test.com/a.jpg")).append("\n");
        builder.append("get file extension from url:").append(FileUtil.getFileExtensionFromUrl("http://test.com/a.jpg")).append("\n");
        builder.append("get file extension from url(/sdcard/0/1.jpg):").append(FileUtil.getFileExtensionFromUrl("/sdcard/0/1.jpg")).append("\n");
        builder.append("private cache dir:").append(FileUtil.getCacheDir()).append("\n");
        builder.append("public DCIM dir:").append(FileUtil.getPublicDCIMDir()).append("\n");
        builder.append("public picture dir:").append(FileUtil.getPublicPictureDir()).append("\n");
        builder.append("public download dir:").append(FileUtil.getPublicDownloadDir()).append("\n");
        builder.append("public music dir:").append(FileUtil.getPublicMusicDir()).append("\n");
        builder.append("public movie dir:").append(FileUtil.getPublicMovieDir()).append("\n");
        builder.append("public dir alarms:").append(FileUtil.getPublicDir(Environment.DIRECTORY_ALARMS)).append("\n");
        text.setText(builder);

        View printDBContent = findViewById(R.id.print_db_content);
        printDBContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StorageManager.getInstance().printCacheContent();
                StorageManager.getInstance().printSettingContent();
            }
        });

        View downloadZcoolApp = findViewById(R.id.view_market_zcool);
        downloadZcoolApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SystemUtil.openMarket("com.zcool.community");
            }
        });

        View openBaiduUrl = findViewById(R.id.view_browser_baidu);
        openBaiduUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SystemUtil.openView("https://www.baidu.com");
            }
        });

        startService(new Intent(this, CommonService.class));
    }

    private String readAsset() {
        try {
            String json = AssetUtil.readAllAsString("acommon/info.json", AvailableUtil.always(), null);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "read fail";
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
