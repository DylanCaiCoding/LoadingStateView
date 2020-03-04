package com.dylanc.loadinghelper.sample.ui;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dylanc.loadinghelper.LoadingHelper;
import com.dylanc.loadinghelper.ViewType;
import com.dylanc.loadinghelper.sample.R;
import com.dylanc.loadinghelper.sample.adapter.LoadingAdapter;
import com.dylanc.loadinghelper.sample.adapter.ScrollDecorAdapter;
import com.dylanc.loadinghelper.sample.utils.HttpUtils;

/**
 * @author Dylan Cai
 * @since 2020/3/4
 */
public class ScrollingTitleActivity extends AppCompatActivity {

  private LoadingHelper loadingHelper;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_scrolling);
    loadingHelper = new LoadingHelper(this);
    loadingHelper.register(ViewType.LOADING, new LoadingAdapter(ViewGroup.LayoutParams.WRAP_CONTENT));
    loadingHelper.setDecorAdapter(new ScrollDecorAdapter("CoordinatorLayout(scrolling)"));
    loadData();
  }

  private void loadData() {
    loadingHelper.showLoadingView();
    HttpUtils.requestSuccess(new HttpUtils.Callback() {
      @Override
      public void onSuccess() {
        loadingHelper.showContentView();
      }

      @Override
      public void onFailure() {
        loadingHelper.showErrorView();
      }
    });
  }
}
