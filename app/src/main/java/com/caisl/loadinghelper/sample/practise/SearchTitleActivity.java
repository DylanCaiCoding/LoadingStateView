package com.caisl.loadinghelper.sample.practise;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;
import com.caisl.loadinghelper.LoadingHelper;
import com.caisl.loadinghelper.sample.R;
import com.caisl.loadinghelper.sample.adapter.NothingAdapter;
import com.caisl.loadinghelper.sample.adapter.SearchTitleAdapter;
import com.caisl.loadinghelper.sample.utils.HttpUtils;

import static com.caisl.loadinghelper.sample.adapter.NothingAdapter.VIEW_TYPE_NOTHING;

/**
 * @author caisl
 * @since 2019/6/25
 */
public class SearchTitleActivity extends AppCompatActivity implements SearchTitleAdapter.OnSearchListener {

  private LoadingHelper mLoadingHelper;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.layout_content);
    mLoadingHelper = new LoadingHelper(this);
    mLoadingHelper.registerTitleAdapter(new SearchTitleAdapter(this));
    mLoadingHelper.registerAdapter(VIEW_TYPE_NOTHING, new NothingAdapter());
    mLoadingHelper.addTitleView();
    mLoadingHelper.showView(VIEW_TYPE_NOTHING);
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
