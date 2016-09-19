package com.lazy.logging.interfaces;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.lazy.logging.app.MyAppication;


public interface BaseViewInterface {

	public void findViews();

	public void setViewListener();

	/**
	 * 加载数据
	 */
	public void processExtraData();

	//规范获取
	@NonNull
	Activity getActivity();

	void startActivity(Class<? extends Activity> clazz);

	@NonNull
	MyAppication getMyAppication();
}