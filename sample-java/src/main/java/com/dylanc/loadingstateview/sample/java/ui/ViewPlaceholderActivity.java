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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dylanc.loadingstateview.LoadingStateView;
import com.dylanc.loadingstateview.ViewType;
import com.dylanc.loadingstateview.sample.java.R;
import com.dylanc.loadingstateview.sample.java.delegate.PlaceholderViewDelegate;
import com.dylanc.loadingstateview.sample.java.delegate.NavIconType;
import com.dylanc.loadingstateview.sample.java.utils.HttpUtils;
import com.dylanc.loadingstateview.sample.java.utils.ToolbarUtils;

/**
 * @author Dylan Cai
 */
public class ViewPlaceholderActivity extends AppCompatActivity {

  private LoadingStateView loadingStateView;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view);
    ToolbarUtils.setToolbar(this,"View(placeholder)", NavIconType.BACK);

    View view = findViewById(R.id.content);
    loadingStateView = new LoadingStateView(view);
    loadingStateView.register(new PlaceholderViewDelegate());

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
        loadingStateView.showLoadingView();
      }
    });
  }

}
