package com.lazy.logcat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

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
    private String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //启动Push服务进程
        Intent intent = new Intent(this, PushService.class);
        intent.setPackage(getPackageName());
        startService(intent);

        final ViewGroup contentMain = findViewById(R.id.content_main);
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


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int logCatLogLevel = Logcat.NOT_SHOW_LOG;
                final List<Character> logLevelCharacterList = new ArrayList<>();
                int childCount = contentMain.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    CheckBox checkBox = (CheckBox) contentMain.getChildAt(i);
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

                Logcat.initialize(MainActivity.this, LGApp.logBuilder.build());

                printLog();
                Snackbar.make(view, "printLog", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
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


        Logcat.v().file().tag(TAG_TOP_5).msg("printf log to file at main thread").out();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Logcat.v().file().tag(TAG_TOP_5).msg("printf log to file not at main thread").out();
            }
        }).start();

        Logcat.v().file().tag(TAG_TOP_6).tag(TAG_TOP_7).msg("标签6和标签7的日志").out();
        Logcat.v().file().tags(TAG_TOP_6, TAG_TOP_7).msg("标签6和标签7的日志").msg("now: ").msgs(System.nanoTime(), System.currentTimeMillis()).out();


        Logcat.v().file("device_info.txt").append(false).tag(TAG_TOP_8).msg("制造商:" + Build.MANUFACTURER)
                .ln().format("型号:%s Android %s", Build.MODEL, Build.VERSION.RELEASE).out();

    }


}
