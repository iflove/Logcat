package com.lazy.logging.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lazy.logging.BuildConfig;
import com.lazy.logging.MyFragmentLifecycle;
import com.lazy.logging.app.MyAppication;
import com.lazy.logging.interfaces.BaseViewInterface;
import com.lazy.logging.interfaces.fragment.FragmentLifecycleCallbacks;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 *
 * @author lazy
 * @date 16/8/14
 */
public abstract class BaseFragment extends Fragment implements BaseViewInterface {
    protected final String TAG = this.getClass().getCanonicalName();
    /**
     * Acitivity对象
     **/
    protected Activity mActivity;
    /**
     * 当前显示的内容
     **/
    protected View mRootView;

    protected LayoutInflater mInflater;
    Unbinder mUnbinder;
    protected FragmentLifecycleCallbacks<Fragment> mMyFragmentLifecycle;

    public BaseFragment() {
        mMyFragmentLifecycle = new MyFragmentLifecycle();
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        mMyFragmentLifecycle.onAttachFragment(this, childFragment);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
        mMyFragmentLifecycle.onActivityAttach(this, activity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRetainInstance(true);//Fragment恢复时会跳过onCreate()和onDestroy()方法 不能在onCreate()中放置一些初始化逻辑
        mMyFragmentLifecycle.onFragmentCreate(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mInflater = inflater;
        if (mRootView == null) {
            mRootView = inflater.inflate(getLayoutId(), container, false);
        }
        mUnbinder = ButterKnife.bind(this, mRootView);
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        findViews();
        setViewListener();
        processExtraData();
        mMyFragmentLifecycle.onFragmentCreateView(this);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMyFragmentLifecycle.onViewCreated(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMyFragmentLifecycle.onActivityCreated(this, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        mMyFragmentLifecycle.onFragmentStarted(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMyFragmentLifecycle.onFragmentResumed(this);
        if (!BuildConfig.DEBUG) {
            MobclickAgent.onResume(this.getContext());
            MobclickAgent.onPageStart(this.getClass().getSimpleName());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mMyFragmentLifecycle.onFragmentPaused(this);
        if (!BuildConfig.DEBUG) {
            MobclickAgent.onPause(this.getContext());
            MobclickAgent.onPageEnd(this.getClass().getSimpleName());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mMyFragmentLifecycle.onFragmentStopped(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mMyFragmentLifecycle.onFragmentDestroyView(this);
        mUnbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMyFragmentLifecycle.onFragmentDestroyed(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mMyFragmentLifecycle.onFragmentDetach(this);
        this.mActivity = null;
    }

    public abstract
    @LayoutRes
    int getLayoutId();

    @Override
    public abstract void findViews();

    @Override
    public abstract void setViewListener();

    @Override
    public abstract void processExtraData();

    @Override
    public void startActivity(Class<? extends Activity> clazz) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivity(intent);
    }

    @NonNull
    @Override
    public MyAppication getMyAppication() {
        return MyAppication.getAppication();
    }
}
