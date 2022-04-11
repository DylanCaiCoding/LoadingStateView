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

package com.dylanc.loadingstateview.sample.java.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dylanc.loadingstateview.LoadingStateView;
import com.dylanc.loadingstateview.OnReloadListener;
import com.dylanc.loadingstateview.sample.java.databinding.LayoutContentBinding;
import com.dylanc.loadingstateview.sample.java.utils.HttpUtils;

/**
 * @author Dylan Cai
 */
@SuppressWarnings("FieldCanBeLocal")
public class EmptyFragment extends Fragment implements OnReloadListener {

  private LayoutContentBinding binding;
  private LoadingStateView loadingStateView;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    binding = LayoutContentBinding.inflate(inflater, container, false);
    loadingStateView = new LoadingStateView(binding.getRoot());
    loadingStateView.setOnReloadListener(this);
    return loadingStateView.getDecorView();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    loadData();
  }

  private void loadData() {
    loadingStateView.showLoadingView();
    HttpUtils.requestSuccess(new HttpUtils.Callback() {
      @Override
      public void onSuccess() {
        loadingStateView.showEmptyView();
      }

      @Override
      public void onFailure() {
        loadingStateView.showErrorView();
      }
    });
  }

  @Override
  public void onReload() {
    loadingStateView.showLoadingView();
    HttpUtils.requestSuccess(new HttpUtils.Callback() {
      @Override
      public void onSuccess() {
        loadingStateView.showEmptyView();
      }

      @Override
      public void onFailure() {
        loadingStateView.showErrorView();
      }
    });
  }
}
