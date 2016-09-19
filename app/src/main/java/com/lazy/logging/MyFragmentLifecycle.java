package com.lazy.logging;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.lazy.library.logging.Logcat;
import com.lazy.logging.interfaces.fragment.FragmentLifecycleCallbacks;

/**
 * Created by Sky on 2016/9/12.
 *
 * @Describe
 */

public class MyFragmentLifecycle implements FragmentLifecycleCallbacks<Fragment> {
    private static final String TAG = "MyFragmentLifecycle";

    @Override
    public void onAttachFragment(Fragment fragment, Fragment childFragment) {
        String simpleName = fragment.getClass().getSimpleName();
        Logcat.d(simpleName + ": onAttachFragment [ childFragment = " + childFragment.getClass().getSimpleName() + " ]", TAG, simpleName);
    }

    @Override
    public void onActivityAttach(Fragment fragment, Activity activity) {
        String simpleName = fragment.getClass().getSimpleName();
        Logcat.d(simpleName + ": onActivityAttach [ activity = " + activity.getClass().getSimpleName() + " ]", TAG, simpleName);
    }

    @Override
    public void onFragmentCreate(Fragment fragment) {
        String simpleName = fragment.getClass().getSimpleName();
        Logcat.d(simpleName + ": onFragmentCreate ", TAG, simpleName);
    }

    @Override
    public void onFragmentCreateView(Fragment fragment) {
        String simpleName = fragment.getClass().getSimpleName();
        Logcat.d(simpleName + ": onFragmentCreateView ", TAG, simpleName);
    }

    @Override
    public void onViewCreated(Fragment fragment) {
        String simpleName = fragment.getClass().getSimpleName();
        Logcat.d(simpleName + ": onViewCreated ", TAG, simpleName);
    }

    @Override
    public void onActivityCreated(Fragment fragment, Bundle savedInstanceState) {
        String simpleName = fragment.getClass().getSimpleName();
        Logcat.d(simpleName + ": onActivityCreated ", TAG, simpleName);
    }

    @Override
    public void onFragmentStarted(Fragment fragment) {
        String simpleName = fragment.getClass().getSimpleName();
        Logcat.d(simpleName + ": onFragmentStarted ", TAG, simpleName);
    }

    @Override
    public void onFragmentResumed(Fragment fragment) {
        String simpleName = fragment.getClass().getSimpleName();
        Logcat.d(simpleName + ": onFragmentResumed ", TAG, simpleName);
    }

    @Override
    public void onFragmentPaused(Fragment fragment) {
        String simpleName = fragment.getClass().getSimpleName();
        Logcat.d(simpleName + ": onFragmentPaused ", TAG, simpleName);
    }

    @Override
    public void onFragmentStopped(Fragment fragment) {
        String simpleName = fragment.getClass().getSimpleName();
        Logcat.d(simpleName + ": onFragmentStopped ", TAG, simpleName);
    }

    @Override
    public void onFragmentSaveInstanceState(Fragment fragment, Bundle outState) {
        String simpleName = fragment.getClass().getSimpleName();
        Logcat.d(simpleName + ": onFragmentSaveInstanceState ", TAG, simpleName);
    }

    @Override
    public void onFragmentDestroyView(Fragment fragment) {
        String simpleName = fragment.getClass().getSimpleName();
        Logcat.d(simpleName + ": onFragmentDestroyView ", TAG, simpleName);
    }

    @Override
    public void onFragmentDestroyed(Fragment fragment) {
        String simpleName = fragment.getClass().getSimpleName();
        Logcat.d(simpleName + ": onFragmentDestroyed ", TAG, simpleName);
    }

    @Override
    public void onFragmentDetach(Fragment fragment) {
        String simpleName = fragment.getClass().getSimpleName();
        Logcat.d(simpleName + ": onFragmentDetach ", TAG, simpleName);
    }
}
