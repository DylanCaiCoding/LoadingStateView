package com.caisl.loadinghelper.sample.widget;

/**
 * @author caisl
 * @since 2019/5/18
 */
public interface ILCEView {
  void showLoadingView();

  void showContentView();

  void showErrorView();

  void showEmptyView();

  void showView(int viewType);

  void notifyDataSetChanged(int viewType);

  void executeMethod(int viewType, Object flag, Object... params);

  <T> T getMethodReturnValue(int viewType, Object flag, Object... params);
}