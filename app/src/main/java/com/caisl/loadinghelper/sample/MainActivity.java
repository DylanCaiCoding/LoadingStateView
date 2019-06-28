package com.caisl.loadinghelper.sample;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.caisl.loadinghelper.LoadingHelper;
import com.caisl.loadinghelper.sample.adapter.SimpleContentAdapter;
import com.caisl.loadinghelper.sample.adapter.TitleAdapter;
import com.caisl.loadinghelper.sample.adapter.TitleConfig;
import com.caisl.loadinghelper.sample.practise.*;

/**
 * @author caisl
 * @since 2019/6/20
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    LoadingHelper loadingHelper = new LoadingHelper(this);
    loadingHelper.registerTitleAdapter(new TitleAdapter("about", this));
    loadingHelper.addTitleView();
  }

  public void onActivityErrorClick(View view) {
    startActivity(new Intent(this, ActErrorActivity.class));
  }

  public void onFragmentEmptyClick(View view) {
    startActivity(new Intent(this, FragmentEmptyActivity.class));
  }

  public void onViewTimeoutClick(View view) {
    startActivity(new Intent(this, ViewPlaceholderActivity.class));
  }

  public void onViewPagerClick(View view) {
    startActivity(new Intent(this, ViewPagerActivity.class));
  }

  public void onRecyclerViewClick(View view) {
    startActivity(new Intent(this, RecyclerViewActivity.class));
  }

  public void onMVPActivityClick(View view) {
    startActivity(new Intent(this, SearchTitleActivity.class));
  }

  @Override
  public void onClick(View v) {
    Uri uri = Uri.parse("https://github.com/CaiShenglang/LoadingHelper");
    Intent intent = new Intent("android.intent.action.VIEW", uri);
    startActivity(intent);
  }
}
