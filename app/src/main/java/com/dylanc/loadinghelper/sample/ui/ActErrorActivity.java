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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dylanc.loadinghelper.LoadingHelper;
import com.dylanc.loadinghelper.sample.R;
import com.dylanc.loadinghelper.sample.base.NavIconType;
import com.dylanc.loadinghelper.sample.utils.HttpUtils;
import com.dylanc.loadinghelper.sample.utils.ToolbarUtils;

/**
 * @author Dylan Cai
 */
public class ActErrorActivity extends AppCompatActivity {

  private LoadingHelper loadingHelper;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.layout_content);
    loadingHelper = ToolbarUtils.setToolbar(this, "Activity(error)", NavIconType.BACK);
    loadingHelper.setOnReloadListener(this::onReload);
    loadData();
  }

  private void loadData() {
    loadingHelper.showLoadingView();
    HttpUtils.requestFailure(new HttpUtils.Callback() {
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

  public void onReload() {
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
