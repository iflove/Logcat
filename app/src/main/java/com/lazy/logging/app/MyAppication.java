package com.lazy.logging.app;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;

import com.lazy.library.logging.Builder;
import com.lazy.library.logging.Logcat;
import com.lazy.logging.MyActivityLifecycle;
import com.lazy.logging.interfaces.IConstant;
import com.lazy.logging.util.StorageUtils;

/**
 * Created by lazy on 16/8/14.
 */
public class MyAppication extends Application implements IConstant {
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
		Logcat.initialize(this);

		//初始化Logcat 配置更多信息
		Builder builder = Logcat.newBuilder();
		builder.logSavePath(StorageUtils.getDiskCacheDir(this, "log")); //设置Log 保存的文件夹
		builder.logCatLogLevel(Logcat.SHOW_INFO_LOG | Logcat.SHOW_ERROR_LOG);//设置日志等级
		builder.fileLogLevel(Logcat.NOT_SHOW_LOG); //不显示Log
		Logcat.initialize(this, builder.build());

		if (IS_DEBUG_ACTIVITYLIFE) {
			mActivityLifecycle = new MyActivityLifecycle();
			this.registerActivityLifecycleCallbacks(mActivityLifecycle);
		}

	}

	@NonNull
	public static MyAppication getAppication() {
		return mApplication;
	}

	@NonNull
	public Context getAppContext() {
		return mContext;
	}

	@NonNull
	public Resources getResources() {
		return mResources;
	}

}
