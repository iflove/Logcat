package com.lazy.logging.interfaces.mvp;

/**
 * @param <T>
 */
public interface BaseView<T> {

	void showMessage(String msg);

	void showLoading();

	void hideLoading();

	void setPresenter(T presenter);
}