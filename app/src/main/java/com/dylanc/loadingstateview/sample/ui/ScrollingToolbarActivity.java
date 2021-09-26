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

package com.dylanc.loadingstateview.sample.ui;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dylanc.loadingstateview.LoadingStateView;
import com.dylanc.loadingstateview.ViewType;
import com.dylanc.loadingstateview.sample.R;
import com.dylanc.loadingstateview.sample.delegate.LoadingViewDelegate;
import com.dylanc.loadingstateview.sample.utils.HttpUtils;
import com.dylanc.loadingstateview.sample.utils.ToolbarUtils;

/**
 * @author Dylan Cai
 */
public class ScrollingToolbarActivity extends AppCompatActivity {

  private LoadingStateView loadingStateView;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_scrolling);
    loadingStateView = ToolbarUtils.setScrollingToolbar(this, "SpecialDecorView(scrolling)");
    loadingStateView.register(ViewType.LOADING, new LoadingViewDelegate(ViewGroup.LayoutParams.WRAP_CONTENT));
    loadData();
  }

  private void loadData() {
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
