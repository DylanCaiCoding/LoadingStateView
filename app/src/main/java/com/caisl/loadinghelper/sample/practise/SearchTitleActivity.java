package com.caisl.loadinghelper.sample.practise;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.caisl.loadinghelper.LoadingHelper;
import com.caisl.loadinghelper.sample.R;
import com.caisl.loadinghelper.sample.adapter.SearchTitleAdapter;
import com.caisl.loadinghelper.sample.utils.HttpUtils;

/**
 * @author caisl
 * @since 2019/6/25
 */
public class SearchTitleActivity extends AppCompatActivity implements SearchTitleAdapter.OnSearchListener {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.layout_content);
    LoadingHelper loadingHelper = new LoadingHelper(this);
    loadingHelper.registerTitleAdapter(new SearchTitleAdapter(this));
    loadingHelper.addTitleView();
  }

  @Override
  public void onSearch(String keyword) {
    Toast.makeText(this, "search: "+keyword, Toast.LENGTH_SHORT).show();
  }
}
