package com.lazy.logging;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.lazy.library.logging.Builder;
import com.lazy.library.logging.Logcat;
import com.lazy.logging.util.StorageUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ViewGroup contentMain = (ViewGroup) findViewById(R.id.content_main);

        final List<Character> characters = Arrays.asList(Logcat.SHOW_VERBOSE_LOG, Logcat.SHOW_DEBUG_LOG, Logcat.SHOW_INFO_LOG, Logcat.SHOW_WARN_LOG, Logcat.SHOW_ERROR_LOG, Logcat.SHOW_JSON_LOG);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //初始化Logcat 配置更多信息
                final Builder builder = Logcat.newBuilder();
                //设置Log 保存的文件夹
                builder.logSavePath(StorageUtils.getDiskCacheDir(MainActivity.this, "log"));

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

                builder.logCatLogLevel(logCatLogLevel);
                builder.fileLogLevel(logCatLogLevel);
                Logcat.initialize(MainActivity.this, builder.build());

                printLog();
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
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
        Logcat.json("{\"code\":0,\"message\":\"success\",\"result\":[{\"id\":0,\"img\":\"http://i0.hdslb.com/bfs/bangumi/a4e0f26fdd24887749ee023c3d6d2f31eafde946.jpg\",\"is_ad\":0,\"link\":\"http://bangumi.bilibili.com/anime/3461\",\"simg\":\"http://i0.hdslb.com/bfs/bangumi/3e62691b8d95944a355378bf3fdcd1de34549966.jpg\",\"title\":\"完结撒花！\"},{\"id\":0,\"img\":\"http://i0.hdslb.com/bfs/bangumi/43daa400687be05e8b5f2ac43ee2efc431859260.jpg\",\"is_ad\":0,\"link\":\"http://bangumi.bilibili.com/anime/191\",\"simg\":\"http://i0.hdslb.com/bfs/bangumi/95ad9d56dd263668ee90dbfc38428ad7d64cccf4.jpg\",\"title\":\"请问您今天要来点兔子吗？\"},{\"id\":0,\"img\":\"http://i0.hdslb.com/bfs/bangumi/bec36122392fa877cbd786547bc1460ee03b6611.jpg\",\"is_ad\":0,\"link\":\"http://bangumi.bilibili.com/anime/1183\",\"simg\":\"http://i0.hdslb.com/bfs/bangumi/62645a31323f50805101d3ff913a022d61a1a9ac.jpg\",\"title\":\"CODE GEASS 反叛的鲁路修\"},{\"id\":0,\"img\":\"http://i0.hdslb.com/bfs/bangumi/2768afe6997c9dab6455c7efac85d14c9c1ff5da.jpg\",\"is_ad\":0,\"link\":\"http://bangumi.bilibili.com/anime/2546\",\"simg\":\"http://i0.hdslb.com/bfs/bangumi/88a29d2e30288816059865b7bcb07cc6b589d7f2.jpg\",\"title\":\"言叶之庭\"},{\"id\":0,\"img\":\"http://i0.hdslb.com/bfs/bangumi/4b68111b04e9cbd32e4d29c28bfb2bf8b5df8505.jpg\",\"is_ad\":0,\"link\":\"http://bangumi.bilibili.com/anime/3151\",\"simg\":\"http://i0.hdslb.com/bfs/bangumi/1f42b344f5077799f797fdc14c26ec7ff4d1758d.jpg\",\"title\":\"异邦人：无皇刃谭\"}]}");

        Logcat.v().msg("msg content").out();
        Logcat.v().msg("msg content").msg("result = ").msg(2 + 2).out();
        Logcat.v().msgs("msgs is content", 1, 2, 3).out();

        Logcat.v().tag(TAG).out();
        Logcat.v().tag(TAG).tags("A", "B").out();
        Logcat.v().tag(TAG).tag("A").tag("B").out();

        Logcat.v().msg("msg content").tag(TAG).msg("result = ").msg(2 + 2).out();
        Logcat.v().msgs("output msgs is ", 1, 2, 3).tag(TAG).out();

        //
        Logcat.v().file().msg("output file msg").out();
        Logcat.v().file().msg("output file msg").msg("result = ").msg(2 + 2).out();
        Logcat.v().file().msgs("output file msgs is ", 1, 2, 3).out();

        Logcat.v().file().tag(TAG).msg("output file msg").out();
        Logcat.v().file().msg("output file msg").tag("xxa").msg("result = ").msg(2 + 2).out();
        Logcat.v().file().msgs("output file msgs is ", 1, 2, 3).tag("A").file("MyLog.txt").msg("当然你也可指定文件名 ").out();


        Logcat.e().msg("output error msg").out().ln().msg("Do you see?").format("a:%s;b:%s", 2, 3).out();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
