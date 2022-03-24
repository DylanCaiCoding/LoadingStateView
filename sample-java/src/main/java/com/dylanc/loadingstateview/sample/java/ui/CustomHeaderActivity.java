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

package com.dylanc.loadingstateview.sample.java.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.dylanc.loadingstateview.sample.java.R;
import com.dylanc.loadingstateview.sample.java.ui.fragment.SimpleFragment;
import com.dylanc.loadingstateview.sample.java.utils.ToolbarUtils;
import com.google.android.material.tabs.TabLayout;

/**
 * @author Dylan Cai
 */
public class CustomHeaderActivity extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_pager);
    ToolbarUtils.setCustomToolbar(this, this::onMessageClick,
        R.drawable.ic_baseline_photo_camera_24, this::onFirstBtnClick,
        R.drawable.ic_baseline_favorite_24, this::onSecondBtnClick);

    // This TabLayout is in the custom toolbar.
    TabLayout tabLayout = findViewById(R.id.tab_layout);
    ViewPager viewPager = findViewById(R.id.view_pager);
    viewPager.setAdapter(new TabPagerAdapter(getSupportFragmentManager()));
    tabLayout.setupWithViewPager(viewPager);
  }

  private void onMessageClick(View view) {
    Toast.makeText(this, "message", Toast.LENGTH_SHORT).show();
  }

  private void onFirstBtnClick(View view) {
    Toast.makeText(this, "camera", Toast.LENGTH_SHORT).show();
  }

  private void onSecondBtnClick(View view) {
    Toast.makeText(this, "favorite", Toast.LENGTH_SHORT).show();
  }

  public static class TabPagerAdapter extends FragmentPagerAdapter {

    public TabPagerAdapter(FragmentManager fm) {
      super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int i) {
      return new SimpleFragment();
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
