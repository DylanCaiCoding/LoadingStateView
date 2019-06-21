package com.caisl.loadinghelper.sample.widget;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import com.caisl.loadinghelper.LoadingHelper;

import static java.util.Objects.requireNonNull;

/**
 * @author caisl
 * @since 2019/6/15
 */
public class LCEFragment extends Fragment implements ILCEView {

  private View mContentView;
  private LoadingHelper mLoadingHelper;

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mContentView = view;
  }

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
    mLoadingHelper =  new LoadingHelper(requireNonNull(mContentView), onCreateContentAdapter());
  }

  public LoadingHelper.ContentAdapter onCreateContentAdapter() {
    return null;
  }
}
