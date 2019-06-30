package com.dylanc.loadinghelper.sample.practise;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.dylanc.loadinghelper.LoadingHelper;
import com.dylanc.loadinghelper.ViewType;
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

  private LoadingHelper mLoadingHelper;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.layout_content);
    mLoadingHelper = new LoadingHelper(this);
    mLoadingHelper.register(ViewType.TITLE, new SearchTitleAdapter(this));
    mLoadingHelper.register(CustomViewType.NOTHING, new NothingAdapter());
    mLoadingHelper.addTitleView();
    mLoadingHelper.showView(CustomViewType.NOTHING);
  }

  @Override
  public void onSearch(String keyword) {
    Toast.makeText(this, "search: " + keyword, Toast.LENGTH_SHORT).show();
    mLoadingHelper.showLoadingView();
    HttpUtils.requestSuccess(mCallback);
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
