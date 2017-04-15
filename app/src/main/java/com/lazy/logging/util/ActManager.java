package com.lazy.logging.util;

import android.app.Activity;

import java.util.Stack;

/**
 * activity堆栈式管理
 */
public class ActManager {

	private static Stack<Activity> activityStack = new Stack<Activity>();

//    private static ActManager instance = new ActManager();;

	private ActManager() {
	}

	/**
	 * 单一实例
	 */
//    public static ActManager getActManager() {
//        if (instance == null) {
//            instance = new ActManager();
//        }
//
//        if (activityStack == null) {
//            activityStack = new Stack<Activity>();
//        }
//
//        return instance;
//    }

	/**
	 * 获取指定的Activity
	 *
	 * @author kymjs
	 */
	public static Activity getActivity(Class<?> cls) {
		if (activityStack != null)
			for (Activity activity : activityStack) {
				if (activity.getClass().equals(cls)) {
					return activity;
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
		Activity activity = activityStack.lastElement();
		return activity;
	}

	/**
	 * 获取当前Activity（堆栈中第一个压入的）
	 */
	public static Activity topActivity() {
		return activityStack.firstElement();
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
	public static void AppExit() {
		try {
			finishAllActivity();
			// 杀死该应用进程
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
