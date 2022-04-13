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

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dylanc.loadingstateview.sample.java.R;
import com.dylanc.loadingstateview.sample.java.delegate.NavIconType;
import com.dylanc.loadingstateview.sample.java.base.BaseActivity;

/**
 * @author Dylan Cai
 */
public class MainActivity extends BaseActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setToolbar(getString(R.string.app_name), NavIconType.NONE, R.menu.menu_about);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if (item.getItemId() == R.id.about) {
      Uri uri = Uri.parse("https://github.com/DylanCaiCoding/LoadingHelper");
      Intent intent = new Intent("android.intent.action.VIEW", uri);
      startActivity(intent);
    }
    return true;
  }

  public void onViewClicked(View view) {
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
      case R.id.btn_custom_header:
        startActivity(new Intent(this, CustomHeaderActivity.class));
        break;
      case R.id.btn_search_header:
        startActivity(new Intent(this, MultipleHeaderActivity.class));
        break;
      case R.id.btn_scrolling:
        startActivity(new Intent(this, ScrollingToolbarActivity.class));
        break;
      case R.id.btn_bottom_editor:
        startActivity(new Intent(this, BottomEditorActivity.class));
        break;
    }
  }
}
