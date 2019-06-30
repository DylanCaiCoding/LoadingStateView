package com.dylanc.loadinghelper.sample.practise;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import com.dylanc.loadinghelper.LoadingHelper;
import com.dylanc.loadinghelper.ViewType;
import com.dylanc.loadinghelper.sample.R;
import com.dylanc.loadinghelper.sample.adapter.TitleAdapter;
import com.dylanc.loadinghelper.sample.adapter.TitleConfig;
import com.dylanc.loadinghelper.sample.practise.fragment.LoadingFragment;

import static com.dylanc.loadinghelper.sample.practise.fragment.LoadingFragment.VIEW_TYPE_TIMEOUT;

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
    loadingHelper.register(ViewType.TITLE, new TitleAdapter("ViewPager(timeout)", TitleConfig.Type.BACK));
    loadingHelper.addTitleView();

    TabLayout tabLayout = findViewById(R.id.tab_layout);
    ViewPager viewPager = findViewById(R.id.view_pager);
    viewPager.setAdapter(new TabPagerAdapter(getSupportFragmentManager()));
    tabLayout.setupWithViewPager(viewPager);
  }

  class TabPagerAdapter extends FragmentPagerAdapter {

    TabPagerAdapter(FragmentManager fm) {
      super(fm);
    }

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
