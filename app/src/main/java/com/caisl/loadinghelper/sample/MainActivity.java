package com.caisl.loadinghelper.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.caisl.loadinghelper.LoadingHelper;
import com.caisl.loadinghelper.sample.practise.*;
import com.caisl.loadinghelper.sample.practise.mvp.MVPLoadActivity;

/**
 * @author caisl
 * @since 2019/6/20
 */
public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    final LoadingHelper loadingHelper = new LoadingHelper(this);
    loadingHelper.addTitleView();
  }

  public void onActivityErrorClick(View view) {
    startActivity(new Intent(this, ActErrorActivity.class));
  }

  public void onFragmentEmptyClick(View view) {
    startActivity(new Intent(this, FragmentEmptyActivity.class));
  }

  public void onViewTimeoutClick(View view) {
    startActivity(new Intent(this, ViewTimeoutActivity.class));
  }

  public void onViewPagerClick(View view) {
    startActivity(new Intent(this, ViewPagerActivity.class));
  }

  public void onRecyclerViewClick(View view) {
    startActivity(new Intent(this, RecyclerViewActivity.class));
  }

  public void onMVPActivityClick(View view) {
    startActivity(new Intent(this, MVPLoadActivity.class));
  }
}
