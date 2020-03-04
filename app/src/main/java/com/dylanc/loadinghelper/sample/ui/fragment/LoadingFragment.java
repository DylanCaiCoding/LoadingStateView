package com.dylanc.loadinghelper.sample.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dylanc.loadinghelper.LoadingHelper;
import com.dylanc.loadinghelper.sample.R;
import com.dylanc.loadinghelper.sample.adapter.EmptyAdapter;
import com.dylanc.loadinghelper.sample.adapter.TimeoutAdapter;
import com.dylanc.loadinghelper.sample.utils.HttpUtils;

/**
 * @author Dylan Cai
 * @since 2019/6/20
 */
public class LoadingFragment extends Fragment implements LoadingHelper.OnReloadListener {

  private static final String KEY_VIEW_TYPE = "view_type";
  public static final int VIEW_TYPE_TIMEOUT = 1;
  public static final int VIEW_TYPE_EMPTY = 2;

  private LoadingHelper loadingHelper;
  private int viewType;

  public static LoadingFragment newInstance(int viewType) {
    Bundle args = new Bundle();
    args.putInt(KEY_VIEW_TYPE, viewType);
    LoadingFragment fragment = new LoadingFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.layout_content, container, false);
    loadingHelper = new LoadingHelper(view);
    loadingHelper.setOnReloadListener(this);
    return loadingHelper.getDecorView();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    if (getArguments() != null) {
      viewType = getArguments().getInt(KEY_VIEW_TYPE);
      switch (viewType) {
        case VIEW_TYPE_TIMEOUT:
          loadingHelper.register(viewType, new TimeoutAdapter());
          break;
        case VIEW_TYPE_EMPTY:
          loadingHelper.register(viewType, new EmptyAdapter());
          break;
      }
    }
    loadData();
  }

  private void loadData() {
    loadingHelper.showLoadingView();
    HttpUtils.requestFailure(new HttpUtils.Callback() {
      @Override
      public void onSuccess() {
        loadingHelper.showContentView();
      }

      @Override
      public void onFailure() {
        loadingHelper.showView(viewType);
      }
    });
  }

  @Override
  public void onReload() {
    loadingHelper.showLoadingView();
    HttpUtils.requestSuccess(new HttpUtils.Callback() {
      @Override
      public void onSuccess() {
        loadingHelper.showContentView();
      }

      @Override
      public void onFailure() {
        loadingHelper.showView(viewType);
      }
    });
  }
}
