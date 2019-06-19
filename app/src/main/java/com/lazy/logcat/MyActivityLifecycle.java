package com.lazy.logcat;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.lazy.library.logging.Logcat;

/**
 * Created by lazy on 16/9/11.
 */
public class MyActivityLifecycle implements Application.ActivityLifecycleCallbacks {
    private static final String TAG = "ActivityLifecycle";

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        String simpleName = activity.getClass().getSimpleName();
        Logcat.i().tag(TAG).tag(simpleName).msg(simpleName).msg(": onActivityCreated").out();
    }

    @Override
    public void onActivityStarted(Activity activity) {
        String simpleName = activity.getClass().getSimpleName();
        Logcat.i().tag(TAG).tag(simpleName).msg(simpleName).msg(": onActivityStarted").out();
    }

    @Override
    public void onActivityResumed(Activity activity) {
        String simpleName = activity.getClass().getSimpleName();
        Logcat.i().tag(TAG).tag(simpleName).msg(simpleName).msg(": onActivityResumed").out();
    }

    @Override
    public void onActivityPaused(Activity activity) {
        String simpleName = activity.getClass().getSimpleName();
        Logcat.i().tag(TAG).tag(simpleName).msg(simpleName).msg(": onActivityPaused").out();
    }

    @Override
    public void onActivityStopped(Activity activity) {
        String simpleName = activity.getClass().getSimpleName();
        Logcat.i().tag(TAG).tag(simpleName).msg(simpleName).msg(": onActivityStopped").out();
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        String simpleName = activity.getClass().getSimpleName();
        Logcat.i().tag(TAG).tag(simpleName).msg(simpleName).msg(": onActivitySaveInstanceState").out();
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        String simpleName = activity.getClass().getSimpleName();
        Logcat.i().tag(TAG).tag(simpleName).msg(simpleName).msg(": onActivityDestroyed").out();
    }

}
