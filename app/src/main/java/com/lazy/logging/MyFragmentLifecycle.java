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
        Logcat.i().tag(TAG).tag(simpleName).msg(": onAttachFragment [ childFragment = " + childFragment.getClass().getSimpleName() + " ]").out();
    }

    @Override
    public void onActivityAttach(Fragment fragment, Activity activity) {
        String simpleName = fragment.getClass().getSimpleName();
        Logcat.i().tag(TAG).tag(simpleName).msg(": onActivityAttach [ activity = " + activity.getClass().getSimpleName() + " ]").out();
    }

    @Override
    public void onFragmentCreate(Fragment fragment) {
        String simpleName = fragment.getClass().getSimpleName();
        Logcat.i().tag(TAG).tag(simpleName).msg(": onFragmentCreate").out();
    }

    @Override
    public void onFragmentCreateView(Fragment fragment) {
        String simpleName = fragment.getClass().getSimpleName();
        Logcat.i().tag(TAG).tag(simpleName).msg(": onFragmentCreateView").out();
    }

    @Override
    public void onViewCreated(Fragment fragment) {
        String simpleName = fragment.getClass().getSimpleName();
        Logcat.i().tag(TAG).tag(simpleName).msg(": onViewCreated").out();
    }

    @Override
    public void onActivityCreated(Fragment fragment, Bundle savedInstanceState) {
        String simpleName = fragment.getClass().getSimpleName();
        Logcat.i().tag(TAG).tag(simpleName).msg(": onActivityCreated").out();
    }

    @Override
    public void onFragmentStarted(Fragment fragment) {
        String simpleName = fragment.getClass().getSimpleName();
        Logcat.i().tag(TAG).tag(simpleName).msg(": onFragmentStarted").out();
    }

    @Override
    public void onFragmentResumed(Fragment fragment) {
        String simpleName = fragment.getClass().getSimpleName();
        Logcat.i().tag(TAG).tag(simpleName).msg(": onFragmentResumed").out();
    }

    @Override
    public void onFragmentPaused(Fragment fragment) {
        String simpleName = fragment.getClass().getSimpleName();
        Logcat.i().tag(TAG).tag(simpleName).msg(": onFragmentPaused").out();
    }

    @Override
    public void onFragmentStopped(Fragment fragment) {
        String simpleName = fragment.getClass().getSimpleName();
        Logcat.i().tag(TAG).tag(simpleName).msg(": onFragmentStopped").out();
    }

    @Override
    public void onFragmentSaveInstanceState(Fragment fragment, Bundle outState) {
        String simpleName = fragment.getClass().getSimpleName();
        Logcat.i().tag(TAG).tag(simpleName).msg(": onFragmentSaveInstanceState").out();
    }

    @Override
    public void onFragmentDestroyView(Fragment fragment) {
        String simpleName = fragment.getClass().getSimpleName();
        Logcat.i().tag(TAG).tag(simpleName).msg(": onFragmentDestroyView").out();
    }

    @Override
    public void onFragmentDestroyed(Fragment fragment) {
        String simpleName = fragment.getClass().getSimpleName();
        Logcat.i().tag(TAG).tag(simpleName).msg(": onFragmentDestroyed").out();
    }

    @Override
    public void onFragmentDetach(Fragment fragment) {
        String simpleName = fragment.getClass().getSimpleName();
        Logcat.i().tag(TAG).tag(simpleName).msg(": onFragmentDetach").out();
    }
}
