package com.lazy.logging.util;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import java.util.Stack;

/**
 * author : rentianlong
 * add date : 2016/12/12
 * activity堆栈式管理
 */
public class ActivityManager {

	private static Stack<Activity> activityStack = new Stack<Activity>();


    private ActivityManager() {
    }


	/**
	 * 获取指定的Activity
	 */
	public static Activity getActivity(Class<?> cls) {
        if (activityStack != null) {
            for (Activity activity : activityStack) {
                if (activity.getClass().equals(cls)) {
                    return activity;
                }
            }
        }
        return null;
	}

	/**
	 * 添加Activity到堆栈
	 */
	public static void addActivity(Activity activity) {
		activityStack.add(activity);
	}

	/**
	 * 获取当前Activity（堆栈中最后一个压入的）
	 */
	public static Activity currentActivity() {
        if (activityStack.isEmpty()) {
            return null;
        }
        return activityStack.lastElement();
    }

	/**
	 * 获取当前Activity（堆栈中第一个压入的）
	 */
	public static Activity topActivity() {
        if (activityStack.isEmpty()) {
            return null;
        }
        return activityStack.firstElement();
	}

    public static boolean activityTopStackIsEmpty() {
        return activityStack.isEmpty();
    }

    public static boolean activityTopStackIsNotEmpty() {
        return activityStack.isEmpty();
    }

	/**
	 * 结束当前Activity（堆栈中最后一个压入的）
	 */
	public static void finishActivity() {
		Activity activity = activityStack.lastElement();
		finishAndRemoveActivity(activity);
	}

	/**
	 * 结束指定的Activity
	 */
	public static void finishAndRemoveActivity(Activity activity) {
		if (activity != null && activityStack.contains(activity)) {
			activityStack.remove(activity);
			activity.finish();
		}
	}

	public static void finishActivity(Activity activity) {
		if (activity != null && activityStack.contains(activity)) {
			activity.finish();
		}
	}

	/**
	 * 结束指定的Activity
	 */
	public static void removeActivity(Activity activity) {
		if (activity != null && activityStack.contains(activity)) {
			activityStack.remove(activity);
		}
	}

	/**
	 * 结束指定类名的Activity
	 */
	public static void finishActivity(Class<?> cls) {
		for (Activity activity : activityStack) {
			if (activity.getClass().equals(cls)) {
				finishAndRemoveActivity(activity);
				break;
			}
		}
	}

	/**
	 * 结束所有Activity
	 */
	public static void finishAllActivity() {
		int size = activityStack.size();
		//FIFO
		for (int i = size - 1; i >= 0; i--) {
			if (null != activityStack.get(i)) {
				finishActivity(activityStack.get(i));
			}
		}
		activityStack.clear();
	}

	/**
	 * 退出应用程序
	 */
    public static void exitApp() {
        try {
			finishAllActivity();
			// 杀死该应用进程
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    @Nullable
    public static FragmentManager getSupportFragmentManager(Class<? extends FragmentActivity> cls) {
        Activity activity = getActivity(cls);
        if (activity instanceof FragmentActivity) {
            return ((FragmentActivity) activity).getSupportFragmentManager();
        }
        return null;
    }
}
