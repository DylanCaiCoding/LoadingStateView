package com.dylanc.loadinghelper.sample.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.dylanc.loadinghelper.LoadingHelper;
import com.dylanc.loadinghelper.ViewType;
import com.dylanc.loadinghelper.sample.R;
import com.dylanc.loadinghelper.sample.adapter.TitleAdapter;
import com.dylanc.loadinghelper.sample.base.TitleConfig;
import com.dylanc.loadinghelper.sample.utils.HttpUtils;

/**
 * @author Dylan Cai
 * @since 2019/6/20
 */
public class ActErrorActivity extends AppCompatActivity {

  private LoadingHelper loadingHelper;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.layout_content);
    loadingHelper = new LoadingHelper(this);
    loadingHelper.setOnReloadListener(this::onReload);
    loadingHelper.register(ViewType.TITLE,new TitleAdapter("Activity(error)", TitleConfig.Type.BACK));
    loadingHelper.setDecorHeader(ViewType.TITLE);
    loadFailure();
  }

  public void onReload() {
    loadSuccess();
  }

  private void loadSuccess() {
    loadingHelper.showLoadingView();
    HttpUtils.requestSuccess(callback);
  }

  private void loadFailure() {
    loadingHelper.showLoadingView();
    HttpUtils.requestFailure(callback);
  }

  private HttpUtils.Callback callback = new HttpUtils.Callback() {
    @Override
    public void onSuccess() {
      loadingHelper.showContentView();
    }

    @Override
    public void onFailure() {
      loadingHelper.showErrorView();
    }
  };
}
