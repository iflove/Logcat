package com.lazy.logcat.app;

import android.app.Application;
import android.os.Process;

import com.lazy.library.logging.Builder;
import com.lazy.library.logging.Logcat;
import com.lazy.library.logging.extend.JLog;
import com.lazy.logcat.MyActivityLifecycle;
import com.lazy.logcat.PushService;
import com.lazy.logcat.demo.BuildConfig;
import com.lazy.logcat.util.StorageUtils;

/**
 * @author lazy
 * @date 16/8/14
 */
public class LGApp extends Application {
    public static final String TAG = "LGApp";
    public static final String TAG_APP_EVENT = "LGApp_Event";
    public static final String TAG_TOP_1 = "标签Top.1";

    public static Builder logBuilder;

    @Override
    public void onCreate() {
        super.onCreate();
        initialize();
        Logcat.d(TAG_APP_EVENT, "Pid:" + Process.myPid());
    }

    private void initialize() {
        //简单初始化Logcat
//        Logcat.initialize(this);

        /*推荐配置*/
        //初始化Logcat 配置更多信息
        Builder builder = Logcat.newBuilder();
        //设置Log 保存的文件夹
        builder.logSavePath(StorageUtils.getDiskCacheDir(this, "log"));
        //设置输出日志等级
        if (BuildConfig.DEBUG) {
            builder.logCatLogLevel(Logcat.SHOW_ALL_LOG);
            //设置输出文件日志等级
            builder.fileLogLevel(Logcat.SHOW_ALL_LOG);
        } else {
            builder.logCatLogLevel(Logcat.SHOW_INFO_LOG | Logcat.SHOW_WARN_LOG | Logcat.SHOW_ERROR_LOG);
            //设置输出文件日志等级
            builder.fileLogLevel(Logcat.SHOW_INFO_LOG | Logcat.SHOW_WARN_LOG | Logcat.SHOW_ERROR_LOG);
        }
        //不显示日志
        //builder.fileLogLevel(Logcat.NOT_SHOW_LOG);

        builder.topLevelTag(TAG_TOP_1);
        //删除过了几天无用日志条目
        builder.deleteUnusedLogEntriesAfterDays(7);
        //输出到Java控制台服务端
        builder.dispatchLog(new JLog("192.168.3.11", 5036));
        //是否自动保存日志到文件中
        builder.autoSaveLogToFile(true);
        //是否显示打印日志调用堆栈信息
        builder.showStackTraceInfo(true);
        //是否显示文件日志的时间
        builder.showFileTimeInfo(true);
        //是否显示文件日志的进程以及Linux线程
        builder.showFilePidInfo(false);
        //是否显示文件日志级别
        builder.showFileLogLevel(true);
        //是否显示文件日志标签
        builder.showFileLogTag(true);
        //是否显示文件日志调用堆栈信息
        builder.showFileStackTraceInfo(false);
        //添加该标签,日志将被写入文件
        builder.addTagToFile(TAG_APP_EVENT);
        builder.addTagToFile(PushService.TAG);
        logBuilder = builder;
        Logcat.initialize(this, builder.build());
        MyActivityLifecycle mActivityLifecycle = new MyActivityLifecycle();
        this.registerActivityLifecycleCallbacks(mActivityLifecycle);
    }

}
