package com.lazy.logcat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;
import com.lazy.library.logging.Logcat;
import com.lazy.logcat.app.LGApp;
import com.lazy.logcat.demo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okio.Okio;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final String TAG_TOP_2 = "标签Top.2";
    public static final String TAG_TOP_3 = "标签Top.3";
    public static final String TAG_TOP_4 = "标签Top.4";
    public static final String TAG_TOP_5 = "标签Top.5";
    public static final String TAG_TOP_6 = "标签Top.6";
    public static final String TAG_TOP_7 = "标签Top.7";
    public static final String TAG_TOP_8 = "标签Top.8";
    public static final String TAG_VIEW_CLICK_EVENT = "View_Click_Event";
    @BindView(R.id.top)
    CheckBox top;
    @BindView(R.id.autoSaveLogToFile)
    CheckBox autoSaveLogToFile;
    @BindView(R.id.showStackTraceInfo)
    CheckBox showStackTraceInfo;
    @BindView(R.id.showFileTimeInfo)
    CheckBox showFileTimeInfo;
    @BindView(R.id.showFilePidInfo)
    CheckBox showFilePidInfo;
    @BindView(R.id.showFileLogLevel)
    CheckBox showFileLogLevel;
    @BindView(R.id.showFileLogTag)
    CheckBox showFileLogTag;
    @BindView(R.id.showFileStackTraceInfo)
    CheckBox showFileStackTraceInfo;

    private String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //启动Push服务进程
        Intent intent = new Intent(this, PushService.class);
        intent.setPackage(getPackageName());
        startService(intent);

        final ViewGroup levelViewGroup = findViewById(R.id.level);
        try {
            InputStream is = getAssets().open("cards.json");
            json = Okio.buffer(Okio.source(is)).readUtf8();
            json = new JSONObject(json).toString();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final List<Character> characters = Arrays.asList(Logcat.SHOW_VERBOSE_LOG, Logcat.SHOW_DEBUG_LOG, Logcat.SHOW_INFO_LOG, Logcat.SHOW_WARN_LOG, Logcat.SHOW_ERROR_LOG);
        Button fab = findViewById(R.id.fab);


        fab.setOnClickListener(view -> {
            Logcat.i(TAG_VIEW_CLICK_EVENT, "view id: fab" + " clicked");

            int logCatLogLevel = Logcat.NOT_SHOW_LOG;
            final List<Character> logLevelCharacterList = new ArrayList<>();
            int childCount = levelViewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                CheckBox checkBox = (CheckBox) levelViewGroup.getChildAt(i);
                if (checkBox.isChecked()) {
                    logLevelCharacterList.add(characters.get(i));
                }
            }

            if (logLevelCharacterList.size() >= 1) {
                logCatLogLevel = logLevelCharacterList.get(0);
            }
            for (int i = 1; i < logLevelCharacterList.size(); i++) {
                logCatLogLevel |= logLevelCharacterList.get(i);
            }
            LGApp.logBuilder.logCatLogLevel(logCatLogLevel);
            LGApp.logBuilder.fileLogLevel(logCatLogLevel);
            if (top.isChecked()) {
                LGApp.logBuilder.topLevelTag(LGApp.TAG_TOP_1);
            } else {
                LGApp.logBuilder.topLevelTag(null);
            }
            //是否自动保存日志到文件中
            LGApp.logBuilder.autoSaveLogToFile(autoSaveLogToFile.isChecked());
            //是否显示打印日志调用堆栈信息
            LGApp.logBuilder.showStackTraceInfo(showStackTraceInfo.isChecked());
            //是否显示文件日志的时间
            LGApp.logBuilder.showFileTimeInfo(showFileTimeInfo.isChecked());
            //是否显示文件日志的进程以及Linux线程
            LGApp.logBuilder.showFilePidInfo(showFilePidInfo.isChecked());
            //是否显示文件日志级别
            LGApp.logBuilder.showFileLogLevel(showFileLogLevel.isChecked());
            //是否显示文件日志标签
            LGApp.logBuilder.showFileLogTag(showFileLogTag.isChecked());
            //是否显示文件日志调用堆栈信息
            LGApp.logBuilder.showFileStackTraceInfo(showFileStackTraceInfo.isChecked());

            Logcat.initialize(MainActivity.this, LGApp.logBuilder.build());

            printLog();
            Snackbar.make(view, "printLog", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        });
        printLog();
    }

    private void printLog() {
        //控制台
        Logcat.v("The is verbose log ");
        Logcat.d("The is debug log");
        Logcat.i("The is info log");
        Logcat.w("The is warn log");
        Logcat.e("The is error log");

        //打印超出限制4000字节的日志
        Logcat.i(TAG_TOP_2, json);
        //JLog JSon字符串格式化
        Logcat.i().tag(TAG_TOP_2).msg("response body:").fmtJSON(json).out();

        Logcat.v().tag(TAG_TOP_3).tag(TAG_TOP_4).msg("标签3和标签4的日志").out();
        Logcat.v().tags(TAG_TOP_3, TAG_TOP_4).msg("标签3和标签4的日志").msg("now: ").msgs(System.nanoTime(), System.currentTimeMillis()).out();


        Logcat.v().file().tag(TAG_TOP_5).msg("printf log to file on main thread").out();
        new Thread(() -> Logcat.v().file().tag(TAG_TOP_5).msg("printf log to file not on main thread").out()).start();

        Logcat.v().file().tag(TAG_TOP_6).tag(TAG_TOP_7).msg("标签6和标签7的日志").out();
        Logcat.v().file().tags(TAG_TOP_6, TAG_TOP_7).msg("标签6和标签7的日志").msg("now: ").msgs(System.nanoTime(), System.currentTimeMillis()).out();


        Logcat.v().file("device_info.txt").append(false).tag(TAG_TOP_8).msg("制造商:" + Build.MANUFACTURER)
                .ln().format("型号:%s Android %s", Build.MODEL, Build.VERSION.RELEASE).out();

    }

    @OnClick({R.id.top, R.id.autoSaveLogToFile, R.id.showStackTraceInfo, R.id.showFileTimeInfo, R.id.showFilePidInfo, R.id.showFileLogLevel, R.id.showFileLogTag, R.id.showFileStackTraceInfo})
    public void onViewClicked(View view) {
        String s = view.toString();
        String idName = s.substring(s.indexOf("/") + 1, s.length() - 1);

        Logcat.i(TAG_VIEW_CLICK_EVENT, "view id:" + idName + " clicked");
    }
}
