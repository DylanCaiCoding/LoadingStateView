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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dylanc.loadingstateview.LoadingStateView;
import com.dylanc.loadingstateview.ViewType;
import com.dylanc.loadingstateview.sample.java.R;
import com.dylanc.loadingstateview.sample.java.delegate.NothingViewDelegate;
import com.dylanc.loadingstateview.sample.java.delegate.SearchHeaderViewDelegate;
import com.dylanc.loadingstateview.sample.java.delegate.ToolbarViewDelegate;
import com.dylanc.loadingstateview.sample.java.delegate.NavIconType;
import com.dylanc.loadingstateview.sample.java.utils.HttpUtils;

/**
 * @author Dylan Cai
 */
public class MultipleHeaderActivity extends AppCompatActivity implements SearchHeaderViewDelegate.OnSearchListener {

  private LoadingStateView loadingStateView;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.layout_content);
    loadingStateView = new LoadingStateView(this);
    loadingStateView.register(new NothingViewDelegate());
    loadingStateView.setDecorHeader(
        new ToolbarViewDelegate("MultipleHeader(search)", NavIconType.BACK),
        new SearchHeaderViewDelegate(this)
    );
    loadingStateView.showEmptyView();
  }

  @Override
  public void onSearch(String keyword) {
    Toast.makeText(this, "search: " + keyword, Toast.LENGTH_SHORT).show();
    loadingStateView.showLoadingView();
    HttpUtils.requestSuccess(new HttpUtils.Callback() {
      @Override
      public void onSuccess() {
        loadingStateView.showContentView();
      }

      @Override
      public void onFailure() {
        loadingStateView.showErrorView();
      }
    });
  }
}
