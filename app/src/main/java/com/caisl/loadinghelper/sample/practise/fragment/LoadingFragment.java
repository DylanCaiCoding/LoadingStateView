package com.caisl.loadinghelper.sample.practise.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.caisl.loadinghelper.LoadingHelper;
import com.caisl.loadinghelper.sample.R;
import com.caisl.loadinghelper.sample.adapter.TimeoutAdapter;
import com.caisl.loadinghelper.sample.utils.HttpUtils;

import static com.caisl.loadinghelper.LoadingHelper.VIEW_TYPE_EMPTY;
import static com.caisl.loadinghelper.sample.adapter.TimeoutAdapter.VIEW_TYPE_TIMEOUT;

/**
 * @author caisl
 * @since 2019/6/20
 */
public class LoadingFragment extends Fragment implements LoadingHelper.OnRetryListener {

  private LoadingHelper mLoadingHelper;
  private static final String KEY_VIEW_TYPE = "view type";
  private int mViewType;

  public static LoadingFragment newInstance(int viewType) {
    Bundle args = new Bundle();
    args.putInt(KEY_VIEW_TYPE,viewType);
    LoadingFragment fragment = new LoadingFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.layout_content, container, false);
    mLoadingHelper = new LoadingHelper(view);
    mLoadingHelper.registerAdapter(VIEW_TYPE_TIMEOUT,new TimeoutAdapter());
    mLoadingHelper.setOnRetryListener(this);
    return mLoadingHelper.getDecorView();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    if (getArguments() != null) {
      mViewType = getArguments().getInt(KEY_VIEW_TYPE);
    }
    loadFailure();
  }

  @Override
  public void onRetry() {
    loadSuccess();
  }

  private void loadSuccess() {
    mLoadingHelper.showLoadingView();
    HttpUtils.requestSuccess(mCallback);
  }

  private void loadFailure() {
    mLoadingHelper.showLoadingView();
    HttpUtils.requestFailure(mCallback);
  }

  private HttpUtils.Callback mCallback = new HttpUtils.Callback() {
    @Override
    public void onSuccess() {
      mLoadingHelper.showContentView();
    }

    @Override
    public void onFailure() {
      if (mViewType == VIEW_TYPE_EMPTY) {
        mLoadingHelper.showEmptyView();
      } else if (mViewType == VIEW_TYPE_TIMEOUT) {
        mLoadingHelper.showView(VIEW_TYPE_TIMEOUT);
      }
    }
  };


}
