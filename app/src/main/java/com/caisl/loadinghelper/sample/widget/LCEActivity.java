package com.caisl.loadinghelper.sample.widget;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import com.caisl.loadinghelper.LoadingHelper;

/**
 * @author caisl
 * @since 2019/6/13
 */
@SuppressLint("Registered")
public class LCEActivity extends AppCompatActivity implements ILCEView {

  private LoadingHelper mLoadingHelper;

  @Override
  public void showLoadingView() {
    getLoadingHelper().showLoadingView();
  }

  @Override
  public void showContentView() {
    getLoadingHelper().showContentView();
  }

  @Override
  public void showErrorView() {
    getLoadingHelper().showErrorView();
  }

  @Override
  public void showEmptyView() {
    getLoadingHelper().showEmptyView();
  }

  @Override
  public void showView(int viewType) {
    getLoadingHelper().showView(viewType);
  }

  @Override
  public void notifyDataSetChanged(int viewType) {
    getLoadingHelper().getAdapter(viewType).notifyDataSetChanged();
  }

  @Override
  public void executeMethod(int viewType, Object flag, Object... params) {
    getLoadingHelper().executeMethod(viewType, flag, params);
  }

  @Override
  public <T> T getMethodReturnValue(int viewType, Object flag, Object... params) {
    return getLoadingHelper().getMethodReturnValue(viewType, flag, params);
  }

  @NonNull
  public LoadingHelper getLoadingHelper() {
    if (mLoadingHelper == null) {
      createLCEView();
    }
    return mLoadingHelper;
  }

  public void createLCEView() {
    mLoadingHelper =  new LoadingHelper(this, onCreateContentAdapter());
  }

  public LoadingHelper.ContentAdapter onCreateContentAdapter() {
    return null;
  }
}
