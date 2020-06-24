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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dylanc.loadinghelper.LoadingHelper;
import com.dylanc.loadinghelper.ViewType;
import com.dylanc.loadinghelper.sample.R;
import com.dylanc.loadinghelper.sample.adapter.NothingAdapter;
import com.dylanc.loadinghelper.sample.adapter.SearchHeaderAdapter;
import com.dylanc.loadinghelper.sample.adapter.ToolbarAdapter;
import com.dylanc.loadinghelper.sample.adapter.NavIconType;
import com.dylanc.loadinghelper.sample.utils.HttpUtils;

/**
 * @author Dylan Cai
 */
public class MultipleHeaderActivity extends AppCompatActivity implements SearchHeaderAdapter.OnSearchListener {

  private static final String VIEW_TYPE_SEARCH = "search_header";
  private LoadingHelper loadingHelper;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.layout_content);
    loadingHelper = new LoadingHelper(this);
    loadingHelper.register(ViewType.TITLE, new ToolbarAdapter("MultipleHeader(search)", NavIconType.BACK));
    loadingHelper.register(VIEW_TYPE_SEARCH, new SearchHeaderAdapter(this));
    loadingHelper.register(ViewType.EMPTY, new NothingAdapter());
    loadingHelper.setDecorHeader(ViewType.TITLE, VIEW_TYPE_SEARCH);
    loadingHelper.showEmptyView();
  }

  @Override
  public void onSearch(String keyword) {
    Toast.makeText(this, "search: " + keyword, Toast.LENGTH_SHORT).show();
    loadingHelper.showLoadingView();
    HttpUtils.requestSuccess(new HttpUtils.Callback() {
      @Override
      public void onSuccess() {
        loadingHelper.showContentView();
      }

      @Override
      public void onFailure() {
        loadingHelper.showErrorView();
      }
    });
  }
}
