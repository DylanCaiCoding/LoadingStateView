package com.dylanc.loadinghelper.sample.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Toast;

import com.dylanc.loadinghelper.LoadingHelper;
import com.dylanc.loadinghelper.ViewType;
import com.dylanc.loadinghelper.sample.R;
import com.dylanc.loadinghelper.sample.adapter.NothingAdapter;
import com.dylanc.loadinghelper.sample.adapter.SearchHeaderAdapter;
import com.dylanc.loadinghelper.sample.adapter.TitleAdapter;
import com.dylanc.loadinghelper.sample.base.TitleConfig;
import com.dylanc.loadinghelper.sample.utils.HttpUtils;

/**
 * @author Dylan Cai
 * @since 2019/6/25
 */
public class MultipleHeaderActivity extends AppCompatActivity implements SearchHeaderAdapter.OnSearchListener {

  private static final String VIEW_TYPE_SEARCH = "search_header";
  private static final String VIEW_TYPE_NOTHING = "nothing";
  private LoadingHelper loadingHelper;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.layout_content);
    loadingHelper = new LoadingHelper(this);
    loadingHelper.register(ViewType.TITLE, new TitleAdapter("MultipleHeader(search)", TitleConfig.Type.BACK));
    loadingHelper.register(VIEW_TYPE_SEARCH, new SearchHeaderAdapter(this));
    loadingHelper.register(VIEW_TYPE_NOTHING, new NothingAdapter());
    loadingHelper.setDecorHeader(ViewType.TITLE, VIEW_TYPE_SEARCH);
    loadingHelper.showView(VIEW_TYPE_NOTHING);
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
