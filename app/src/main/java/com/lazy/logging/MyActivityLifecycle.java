package com.lazy.logging;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.lazy.library.logging.Logcat;

/**
 * Created by lazy on 16/9/11.
 */
public class MyActivityLifecycle implements Application.ActivityLifecycleCallbacks {
    //
    private static final String TAG = "MyActivityLifecycle";

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        String simpleName = activity.getClass().getSimpleName();
        Logcat.d(simpleName + ": onActivityCreated", TAG, simpleName);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        String simpleName = activity.getClass().getSimpleName();
        Logcat.d(simpleName + ": onActivityStarted", TAG, simpleName);
    }

    @Override
    public void onActivityResumed(Activity activity) {
        String simpleName = activity.getClass().getSimpleName();
        Logcat.d(simpleName + ": onActivityResumed", TAG, simpleName);
    }

    @Override
    public void onActivityPaused(Activity activity) {
        String simpleName = activity.getClass().getSimpleName();
        Logcat.d(simpleName + ": onActivityPaused", TAG, simpleName);
    }

    @Override
    public void onActivityStopped(Activity activity) {
        String simpleName = activity.getClass().getSimpleName();
        Logcat.d(simpleName + ": onActivityStopped", TAG, simpleName);
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        String simpleName = activity.getClass().getSimpleName();
        Logcat.d(simpleName + ": onActivitySaveInstanceState", TAG, simpleName);
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        String simpleName = activity.getClass().getSimpleName();
        Logcat.d(simpleName + ": onActivityDestroyed", TAG, simpleName);
    }

}
