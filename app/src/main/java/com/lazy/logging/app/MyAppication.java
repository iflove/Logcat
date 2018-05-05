package com.lazy.logging.app;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;

import com.lazy.library.logging.Builder;
import com.lazy.library.logging.Logcat;
import com.lazy.logging.BuildConfig;
import com.lazy.logging.MyActivityLifecycle;
import com.lazy.logging.util.StorageUtils;

/**
 * @author lazy
 * @date 16/8/14
 */
public class MyAppication extends Application {
    public static final String TAG = "MyAppication";

    private static MyAppication mApplication;
    private Context mContext;
    private Resources mResources;

    private MyActivityLifecycle mActivityLifecycle;

    @Override
    public void onCreate() {
        super.onCreate();
        initialize();
    }

    private void initialize() {
        mApplication = this;
        mContext = this.getApplicationContext();
        mResources = this.getResources();
        //初始化Logcat
//        Logcat.initialize(this);

        /*推荐配置*/
        //初始化Logcat 配置更多信息
        Builder builder = Logcat.newBuilder();
        //设置Log 保存的文件夹
        builder.logSavePath(StorageUtils.getDiskCacheDir(this, "log"));
        //设置输出日志等级
        if (BuildConfig.DEBUG) {
            builder.logCatLogLevel(Logcat.SHOW_ALL_LOG);
        } else {
            builder.logCatLogLevel(Logcat.SHOW_INFO_LOG | Logcat.SHOW_WARN_LOG | Logcat.SHOW_ERROR_LOG);
        }
        builder.topLevelTag("Root");
        //设置输出文件日志等级
        builder.fileLogLevel(Logcat.NOT_SHOW_LOG);

        Logcat.initialize(this, builder.build());

        mActivityLifecycle = new MyActivityLifecycle();
        this.registerActivityLifecycleCallbacks(mActivityLifecycle);
    }

    @NonNull
    public static MyAppication getAppication() {
        return mApplication;
    }

    @NonNull
    public Context getAppContext() {
        return mContext;
    }

    @Override
    @NonNull
    public Resources getResources() {
        return mResources;
    }

}
