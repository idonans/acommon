package com.idonans.acommon.demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.idonans.acommon.app.CommonActivity;
import com.idonans.acommon.app.CommonService;
import com.idonans.acommon.data.ProcessManager;
import com.idonans.acommon.data.StorageManager;
import com.idonans.acommon.lang.CommonLog;
import com.idonans.acommon.lang.SoftKeyboardObserver;
import com.idonans.acommon.util.AssetUtil;
import com.idonans.acommon.util.AvailableUtil;
import com.idonans.acommon.util.DimenUtil;
import com.idonans.acommon.util.FileUtil;
import com.idonans.acommon.util.HumanUtil;
import com.idonans.acommon.util.MD5Util;
import com.idonans.acommon.util.RegexUtil;
import com.idonans.acommon.util.SystemUtil;
import com.idonans.acommon.util.TimeUtil;
import com.idonans.acommon.util.ViewUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends CommonActivity {

    private static final String EXTRA_OUT_PHOTO_PATH = "EXTRA_OUT_PHOTO_PATH";
    private static final int REQUEST_CODE_TAKE_PHOTO = 1;
    private SoftKeyboardObserver mSoftKeyboardObserver;

    private File[] mOutPhotos = new File[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SystemUtil.setStatusBarTransparent(getWindow());

        setContentView(R.layout.activity_main);

        TextView text = ViewUtil.findViewByID(this, R.id.text);

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
        builder.append("auto time zone:").append(TimeUtil.isSystemAutoTimeZone()).append("\n");
        builder.append("auto time:").append(TimeUtil.isSystemAutoTime()).append("\n");
        text.setText(builder);

        View printDBContent = ViewUtil.findViewByID(this, R.id.print_db_content);
        printDBContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StorageManager.getInstance().printCacheContent();
                StorageManager.getInstance().printSettingContent();
            }
        });

        View downloadZcoolApp = ViewUtil.findViewByID(this, R.id.view_market_zcool);
        downloadZcoolApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SystemUtil.openMarket("com.zcool.community");
            }
        });

        View openBaiduUrl = ViewUtil.findViewByID(this, R.id.view_browser_baidu);
        openBaiduUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SystemUtil.openView("https://www.baidu.com");
            }
        });

        startService(new Intent(this, CommonService.class));

        final TextView showSoftKeyboardStatus = ViewUtil.findViewByID(this, R.id.softKeyboardStatus);
        View checkSoftKeyboardStatus = ViewUtil.findViewByID(this, R.id.checkSoftKeyboardStatus);
        checkSoftKeyboardStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean softKeyboardShown = SystemUtil.isSoftKeyboardShown(MainActivity.this);
                String softKeyboardStatus;
                if (softKeyboardShown) {
                    softKeyboardStatus = "软键盘打开";
                } else {
                    softKeyboardStatus = "软键盘关闭";
                }
                showSoftKeyboardStatus.setText(softKeyboardStatus);
            }
        });

        mSoftKeyboardObserver = new SoftKeyboardObserver(new SoftKeyboardObserver.SoftKeyboardListener() {
            @Override
            public void onSoftKeyboardOpen() {
                showSoftKeyboardStatus.setText("观察到软键盘打开");
            }

            @Override
            public void onSoftKeyboardClose() {
                showSoftKeyboardStatus.setText("观察到软键盘关闭");
            }
        });
        mSoftKeyboardObserver.register(this);

        final View takePhoto = ViewUtil.findViewByID(this, R.id.take_photo);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });

        View view = ViewUtil.findViewByID(this, R.id.open_contain);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ContainActivity.class);
                startActivity(intent);
            }
        });

        View customKeyboard = ViewUtil.findViewByID(this, R.id.open_custom_keyboard);
        customKeyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = FragmentContainerActivity.newIntent(MainActivity.this, CustomKeyboardFragment.class);
                startActivity(intent);
            }
        });

        fetchDraweeView((SimpleDraweeView) ViewUtil.findViewByID(this, R.id.drawee_view));
    }

    private void fetchDraweeView(SimpleDraweeView draweeView) {
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(ImageRequest.fromUri("http://img.zcool.cn/community/01b726575ccbe20000012e7eaf0b0b.jpg@900w_1l_2o"))
                .setOldController(draweeView.getController())
                .build();
        draweeView.setController(controller);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String outPhotoPath = savedInstanceState.getString(EXTRA_OUT_PHOTO_PATH);
        if (!TextUtils.isEmpty(outPhotoPath)) {
            mOutPhotos[0] = new File(outPhotoPath);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mOutPhotos[0] != null) {
            outState.putString(EXTRA_OUT_PHOTO_PATH, mOutPhotos[0].getAbsolutePath());
        } else {
            outState.putString(EXTRA_OUT_PHOTO_PATH, null);
        }
    }

    private void takePhoto() {
        int takePhotoResult = SystemUtil.takePhoto(this, REQUEST_CODE_TAKE_PHOTO, mOutPhotos);
        CommonLog.d("takePhotoResult:" + takePhotoResult);
        switch (takePhotoResult) {
            case SystemUtil.TAKE_PHOTO_RESULT_CAMERA_NOT_FOUND:
                Toast.makeText(this, "照相机不可用", Toast.LENGTH_LONG).show();
                break;
            case SystemUtil.TAKE_PHOTO_RESULT_SDCARD_ERROR:
                Toast.makeText(this, "SD卡不可用", Toast.LENGTH_LONG).show();
                break;
            case SystemUtil.TAKE_PHOTO_RESULT_OK:
            default:
                break;
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSoftKeyboardObserver.unregister();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_TAKE_PHOTO) {
            CommonLog.d("分析拍照结果...");
            if (resultCode == RESULT_OK) {
                File outPhoto = mOutPhotos[0];
                if (FileUtil.isFile(outPhoto)) {
                    CommonLog.d("拍照成功，文件大小是 " + HumanUtil.getHumanSizeFromByte(outPhoto.length()));
                }
            }
        }
    }

}
