package com.dylanc.loadinghelper.sample.practise;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.dylanc.loadinghelper.LoadingHelper;
import com.dylanc.loadinghelper.ViewType;
import com.dylanc.loadinghelper.sample.R;
import com.dylanc.loadinghelper.sample.adapter.TitleAdapter;
import com.dylanc.loadinghelper.sample.adapter.TitleConfig;
import com.dylanc.loadinghelper.sample.utils.HttpUtils;

/**
 * @author Dylan Cai
 * @since 2019/6/20
 */
public class ActErrorActivity extends AppCompatActivity implements LoadingHelper.OnReloadListener {

  private LoadingHelper mLoadingHelper;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.layout_content);
    mLoadingHelper = new LoadingHelper(this);
    mLoadingHelper.setOnReloadListener(this);
    mLoadingHelper.register(ViewType.TITLE,new TitleAdapter("Activity(error)", TitleConfig.Type.BACK));
    mLoadingHelper.addTitleView();
    loadFailure();
  }

  @Override
  public void onReload() {
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
      mLoadingHelper.showErrorView();
    }
  };
}
