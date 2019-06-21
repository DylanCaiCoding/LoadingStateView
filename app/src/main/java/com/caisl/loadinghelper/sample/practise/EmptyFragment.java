package com.caisl.loadinghelper.sample.practise;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.caisl.loadinghelper.LoadingHelper;
import com.caisl.loadinghelper.sample.R;

/**
 * @author caisl
 * @since 2019/6/20
 */
public class EmptyFragment extends Fragment {

  private LoadingHelper mLoadingHelper;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.layout_content, container, false);
    mLoadingHelper = new LoadingHelper(view);
    return mLoadingHelper.getParent();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mLoadingHelper.setRetryTask(new Runnable() {
      @Override
      public void run() {
        loadSuccess();
      }
    });
    loadFailure();
  }

  private void loadSuccess() {
    mLoadingHelper.showLoadingView();
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        mLoadingHelper.showContentView();
      }
    },2000);
  }

  private void loadFailure() {
    mLoadingHelper.showLoadingView();
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        mLoadingHelper.showEmptyView();
      }
    },2000);
  }


}
