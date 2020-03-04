package com.dylanc.loadinghelper.sample.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;

import com.dylanc.loadinghelper.sample.R;
import com.dylanc.loadinghelper.sample.base.BaseActivity;
import com.dylanc.loadinghelper.sample.base.TitleConfig;

/**
 * @author Dylan Cai
 * @since 2019/6/20
 */
public class MainActivity extends BaseActivity {

  @Override
  public void initContentView() {
    setContentView(R.layout.activity_main);
  }

  @Override
  public void initData(@Nullable Bundle savedInstanceState) {
  }

  @Override
  public void initViews() {
    setToolbar("LoadingHelper", TitleConfig.Type.NO_BACK, R.menu.menu_about, this::onMenuItemClick);
  }

  public void onBtnClicked(View view) {
    switch (view.getId()) {
      case R.id.btn_activity_error:
        startActivity(new Intent(this, ActErrorActivity.class));
        break;
      case R.id.btn_fragment_empty:
        startActivity(new Intent(this, FragmentEmptyActivity.class));
        break;
      case R.id.btn_view_placeholder:
        startActivity(new Intent(this, ViewPlaceholderActivity.class));
        break;
      case R.id.btn_viewpager_timeout:
        startActivity(new Intent(this, ViewPagerActivity.class));
        break;
      case R.id.btn_recyclerview_loading:
        startActivity(new Intent(this, RecyclerViewActivity.class));
        break;
      case R.id.btn_search_header:
        startActivity(new Intent(this, MultipleHeaderActivity.class));
        break;
      case R.id.btn_scrolling:
        startActivity(new Intent(this, ScrollingTitleActivity.class));
        break;
    }
  }

  public boolean onMenuItemClick(MenuItem item) {
    if (item.getItemId() == R.id.about) {
      Uri uri = Uri.parse("https://github.com/DylanCaiCoding/LoadingHelper");
      Intent intent = new Intent("android.intent.action.VIEW", uri);
      startActivity(intent);
    }
    return true;
  }
}
