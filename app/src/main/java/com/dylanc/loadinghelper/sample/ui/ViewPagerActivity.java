/*
 * Copyright (c) 2019. Dylan Cai
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dylanc.loadinghelper.sample.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.dylanc.loadinghelper.sample.R;
import com.dylanc.loadinghelper.sample.base.NavIconType;
import com.dylanc.loadinghelper.sample.ui.fragment.LoadingFragment;
import com.dylanc.loadinghelper.sample.utils.ToolbarUtils;
import com.google.android.material.tabs.TabLayout;

import static com.dylanc.loadinghelper.sample.ui.fragment.LoadingFragment.VIEW_TYPE_TIMEOUT;

/**
 * @author Dylan Cai
 */
public class ViewPagerActivity extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_tab_layout);
    ToolbarUtils.setToolbar(this,"ViewPager(timeout)", NavIconType.BACK);

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
