package com.dylanc.loadinghelper.sample.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

  private LoadingHelper viewLoadingHelper;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view);
    LoadingHelper loadingHelper = new LoadingHelper(this);
    loadingHelper.register(ViewType.TITLE, new TitleAdapter("View(placeholder)", TitleConfig.Type.BACK));
    loadingHelper.setDecorHeader(ViewType.TITLE);

    View view = findViewById(R.id.content);
    viewLoadingHelper = new LoadingHelper(view);
    viewLoadingHelper.register(ViewType.LOADING, new PlaceholderAdapter());

    loadData();
  }

  private void loadData() {
    viewLoadingHelper.showLoadingView();
    HttpUtils.requestSuccess(new HttpUtils.Callback() {
      @Override
      public void onSuccess() {
        viewLoadingHelper.showContentView();
      }

      @Override
      public void onFailure() {
        viewLoadingHelper.showLoadingView();
      }
    });
  }

}
