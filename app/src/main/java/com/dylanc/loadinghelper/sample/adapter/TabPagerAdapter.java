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

package com.dylanc.loadinghelper.sample.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.dylanc.loadinghelper.sample.ui.fragment.TimeoutFragment;

/**
 * @author Dylan Cai
 */
public class TabPagerAdapter extends FragmentPagerAdapter {

  public TabPagerAdapter(FragmentManager fm) {
    super(fm);
  }

  @NonNull
  @Override
  public Fragment getItem(int i) {
    return new TimeoutFragment();
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