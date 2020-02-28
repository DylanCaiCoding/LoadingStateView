package com.dylanc.loadinghelper.sample.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.dylanc.loadinghelper.LoadingHelper;
import com.dylanc.loadinghelper.sample.R;
import com.dylanc.loadinghelper.sample.adapter.CustomViewType;
import com.dylanc.loadinghelper.sample.adapter.NothingAdapter;
import com.dylanc.loadinghelper.sample.adapter.SearchTitleAdapter;
import com.dylanc.loadinghelper.sample.utils.HttpUtils;

/**
 * @author Dylan Cai
 * @since 2019/6/25
 */
public class SearchTitleActivity extends AppCompatActivity implements SearchTitleAdapter.OnSearchListener {

  private static final String VIEW_TYPE_SEARCH = "search_title";
  private LoadingHelper loadingHelper;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.layout_content);
    loadingHelper = new LoadingHelper(this);
    loadingHelper.register(VIEW_TYPE_SEARCH, new SearchTitleAdapter(this));
    loadingHelper.register(CustomViewType.NOTHING, new NothingAdapter());
    loadingHelper.setDecorHeader(VIEW_TYPE_SEARCH);
    loadingHelper.showView(CustomViewType.NOTHING);
  }

  @Override
  public void onSearch(String keyword) {
    Toast.makeText(this, "search: " + keyword, Toast.LENGTH_SHORT).show();
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
