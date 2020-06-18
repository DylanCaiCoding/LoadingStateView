package com.dylanc.loadinghelper.sample.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.dylanc.loadinghelper.LoadingHelper;
import com.dylanc.loadinghelper.ViewType;
import com.dylanc.loadinghelper.sample.R;
import com.dylanc.loadinghelper.sample.adapter.TitleAdapter;
import com.dylanc.loadinghelper.sample.base.NavIconType;
import com.dylanc.loadinghelper.sample.ui.fragment.LoadingFragment;
import com.google.android.material.tabs.TabLayout;

import static com.dylanc.loadinghelper.sample.ui.fragment.LoadingFragment.VIEW_TYPE_TIMEOUT;

/**
 * @author Dylan Cai
 * @since 2019/6/20
 */
public class ViewPagerActivity extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_tab_layout);
    LoadingHelper loadingHelper = new LoadingHelper(this);
    loadingHelper.register(ViewType.TITLE, new TitleAdapter("ViewPager(timeout)", NavIconType.BACK));
    loadingHelper.setDecorHeader(ViewType.TITLE);

    TabLayout tabLayout = findViewById(R.id.tab_layout);
    ViewPager viewPager = findViewById(R.id.view_pager);
    viewPager.setAdapter(new TabPagerAdapter(getSupportFragmentManager()));
    tabLayout.setupWithViewPager(viewPager);
  }

  static class TabPagerAdapter extends FragmentPagerAdapter {

    TabPagerAdapter(FragmentManager fm) {
      super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int i) {
      return LoadingFragment.newInstance(VIEW_TYPE_TIMEOUT);
    }

    @Override
    public int getCount() {
      return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
      return "tab " + position;
    }
  }
}
