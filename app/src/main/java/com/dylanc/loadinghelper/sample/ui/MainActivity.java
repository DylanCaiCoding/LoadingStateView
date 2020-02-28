package com.dylanc.loadinghelper.sample.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dylanc.loadinghelper.LoadingHelper;
import com.dylanc.loadinghelper.sample.R;
import com.dylanc.loadinghelper.sample.base.BaseActivity;

import org.jetbrains.annotations.NotNull;

/**
 * @author Dylan Cai
 * @since 2019/6/20
 */
public class MainActivity extends BaseActivity  {

  @Override
  public void initContentView() {
    setContentView(R.layout.activity_main);
  }

  @Override
  public void initData(@Nullable Bundle savedInstanceState) {
  }

  @Override
  public void initViews() {
    addTitleView("LoadingHelper", "about", this::onRightBtnClick);
  }

  public void onRightBtnClick(View v) {
    Uri uri = Uri.parse("https://github.com/DylanCaiCoding/LoadingHelper");
    Intent intent = new Intent("android.intent.action.VIEW", uri);
    startActivity(intent);
  }

  public void onActivityErrorClick(View view) {
    startActivity(ActErrorActivity.class);
  }

  public void onFragmentEmptyClick(View view) {
    startActivity(FragmentEmptyActivity.class);
  }

  public void onViewTimeoutClick(View view) {
    startActivity(ViewPlaceholderActivity.class);
  }

  public void onViewPagerClick(View view) {
    startActivity(ViewPagerActivity.class);
  }

  public void onRecyclerViewClick(View view) {
    startActivity(RecyclerViewActivity.class);
  }

  public void onMVPActivityClick(View view) {
    startActivity(SearchTitleActivity.class);
  }

  private void startActivity(Class<?> clazz) {
    startActivity(new Intent(this, clazz));
  }
}
