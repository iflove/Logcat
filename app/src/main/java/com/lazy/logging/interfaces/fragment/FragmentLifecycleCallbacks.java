package com.lazy.logging.interfaces.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public interface FragmentLifecycleCallbacks<F> {

    void onAttachFragment(F fragment, Fragment childFragment);

    void onActivityAttach(F fragment, Activity activity);

    void onFragmentCreate(F fragment);

    void onFragmentCreateView(F fragment);

    void onViewCreated(F fragment);

    void onActivityCreated(F fragment, Bundle savedInstanceState);

    void onFragmentStarted(F fragment);

    void onFragmentResumed(F fragment);

    void onFragmentPaused(F fragment);

    void onFragmentStopped(F fragment);

    void onFragmentSaveInstanceState(F fragment, Bundle outState);

    void onFragmentDestroyView(F fragment);

    void onFragmentDestroyed(F fragment);

    void onFragmentDetach(F fragment);

}