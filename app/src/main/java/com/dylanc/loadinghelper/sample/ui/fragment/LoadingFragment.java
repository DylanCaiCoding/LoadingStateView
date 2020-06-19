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

package com.dylanc.loadinghelper.sample.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dylanc.loadinghelper.LoadingHelper;
import com.dylanc.loadinghelper.sample.R;
import com.dylanc.loadinghelper.sample.adapter.EmptyAdapter;
import com.dylanc.loadinghelper.sample.adapter.TimeoutAdapter;
import com.dylanc.loadinghelper.sample.utils.HttpUtils;

/**
 * @author Dylan Cai
 */
public class LoadingFragment extends Fragment implements LoadingHelper.OnReloadListener {

  private static final String KEY_VIEW_TYPE = "view_type";
  public static final int VIEW_TYPE_TIMEOUT = 1;
  public static final int VIEW_TYPE_EMPTY = 2;

  private LoadingHelper loadingHelper;
  private int viewType;

  public static LoadingFragment newInstance(int viewType) {
    Bundle args = new Bundle();
    args.putInt(KEY_VIEW_TYPE, viewType);
    LoadingFragment fragment = new LoadingFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.layout_content, container, false);
    loadingHelper = new LoadingHelper(view);
    loadingHelper.setOnReloadListener(this);
    return loadingHelper.getDecorView();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    if (getArguments() != null) {
      viewType = getArguments().getInt(KEY_VIEW_TYPE);
      switch (viewType) {
        case VIEW_TYPE_TIMEOUT:
          loadingHelper.register(viewType, new TimeoutAdapter());
          break;
        case VIEW_TYPE_EMPTY:
          loadingHelper.register(viewType, new EmptyAdapter());
          break;
      }
    }
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
        loadingHelper.showView(viewType);
      }
    });
  }

  @Override
  public void onReload() {
    loadingHelper.showLoadingView();
    HttpUtils.requestSuccess(new HttpUtils.Callback() {
      @Override
      public void onSuccess() {
        loadingHelper.showContentView();
      }

      @Override
      public void onFailure() {
        loadingHelper.showView(viewType);
      }
    });
  }
}
