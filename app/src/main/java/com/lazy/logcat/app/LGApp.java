package com.lazy.logcat.app;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.os.Process;
import android.support.annotation.NonNull;

import com.lazy.library.logging.Builder;
import com.lazy.library.logging.Config;
import com.lazy.library.logging.Logcat;
import com.lazy.logcat.MyActivityLifecycle;
import com.lazy.logcat.demo.BuildConfig;
import com.lazy.logcat.util.StorageUtils;

/**
 * @author lazy
 * @date 16/8/14
 */
public class LGApp extends Application {
    public static final String TAG = "LGApp";
    public static final String TAG_APP_EVENT = "APP_Event";

    private static LGApp mApplication;
    private Context mContext;
    private Resources mResources;
    private Config config;

    @Override
    public void onCreate() {
        super.onCreate();
        initialize();
        Logcat.d(TAG_APP_EVENT, "Pid:" + Process.myPid());
    }

    private void initialize() {
        mApplication = this;
        mContext = this.getApplicationContext();
        mResources = this.getResources();
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

        builder.topLevelTag("标签Top.1");
        //删除过了几天无用日志条目
        builder.deleteUnusedLogEntriesAfterDays(7);
        config = builder.build();
        Logcat.initialize(this, config);
        MyActivityLifecycle mActivityLifecycle = new MyActivityLifecycle();
        this.registerActivityLifecycleCallbacks(mActivityLifecycle);
    }

    @NonNull
    public static LGApp getAppication() {
        return mApplication;
    }

    @NonNull
    public Context getAppContext() {
        return mContext;
    }

}
