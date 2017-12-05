package com.lazy.logging.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.lazy.logging.BuildConfig;
import com.lazy.logging.app.MyAppication;
import com.lazy.logging.interfaces.BaseViewInterface;
import com.lazy.logging.util.ActivityManager;
import com.umeng.analytics.MobclickAgent;


/**
 *
 * @author lazy
 * @date 16/9/11
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseViewInterface {

    protected final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.addActivity(this);
        if (!BuildConfig.DEBUG) {
            MobclickAgent.setDebugMode(false);
            MobclickAgent.openActivityDurationTrack(false);
            //发送策略定义了用户由统计分析SDK产生的数据发送回友盟服务器的频率。
//			MobclickAgent.updateOnlineConfig(this);
        }
        findViews();
        setViewListener();
        processExtraData();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
//		setIntent(intent);
        processExtraData();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!BuildConfig.DEBUG) {
            MobclickAgent.onResume(this);
            MobclickAgent.onPageStart(this.getClass().getSimpleName());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!BuildConfig.DEBUG) {
            MobclickAgent.onPause(this);
            MobclickAgent.onPageEnd(this.getClass().getSimpleName());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.removeActivity(this);
    }

    /**
     * 绑定 View 控件
     *
     * @param id
     * @param <T>
     * @return
     */
    protected <T> T bindViewById(@IdRes int id) {
        //noinspection unchecked
        return (T) super.findViewById(id);
    }

    @Override
    public abstract void findViews();

    @Override
    public abstract void setViewListener();

    @Override
    public abstract void processExtraData();

    @NonNull
    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void startActivity(Class<? extends Activity> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    @NonNull
    @Override
    public MyAppication getMyAppication() {
        return MyAppication.getAppication();
    }
}
