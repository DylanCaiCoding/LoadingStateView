package com.dylanc.loadinghelper.sample.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.dylanc.loadinghelper.LoadingHelper;
import com.dylanc.loadinghelper.ViewType;
import com.dylanc.loadinghelper.sample.R;
import com.dylanc.loadinghelper.sample.adapter.PlaceholderAdapter;
import com.dylanc.loadinghelper.sample.adapter.TitleAdapter;
import com.dylanc.loadinghelper.sample.base.TitleConfig;
import com.dylanc.loadinghelper.sample.utils.HttpUtils;

/**
 * @author Dylan Cai
 * @since 2019/6/20
 */
public class ViewPlaceholderActivity extends AppCompatActivity {

  private LoadingHelper mViewLoadingHelper;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view);
    LoadingHelper loadingHelper = new LoadingHelper(this);
    loadingHelper.register(ViewType.TITLE, new TitleAdapter("View(placeholder)", TitleConfig.Type.BACK));
    loadingHelper.addTitleView();

    mViewLoadingHelper = new LoadingHelper(findViewById(R.id.content));
    mViewLoadingHelper.register(ViewType.LOADING, new PlaceholderAdapter());
    loadSuccess();
  }

  private void loadSuccess() {
    mViewLoadingHelper.showLoadingView();
    HttpUtils.requestSuccess(mCallback);
  }

  private HttpUtils.Callback mCallback = new HttpUtils.Callback() {
    @Override
    public void onSuccess() {
      mViewLoadingHelper.showContentView();
    }

    @Override
    public void onFailure() {
      mViewLoadingHelper.showLoadingView();
    }
  };

}
