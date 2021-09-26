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

package com.dylanc.loadingstateview.sample.viewdelegate;

import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dylanc.loadingstateview.LoadingStateView;
import com.dylanc.loadingstateview.sample.R;

/**
 * @author Dylan Cai
 */
public class TimeoutViewDelegate extends LoadingStateView.ViewDelegate<LoadingStateView.ViewHolder> {

  @NonNull
  @Override
  public LoadingStateView.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
    return new LoadingStateView.ViewHolder(inflater.inflate(R.layout.layout_timeout, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull LoadingStateView.ViewHolder holder) {
    holder.getRootView().setOnClickListener(v -> {
      if (holder.getOnReloadListener() != null) {
        holder.getOnReloadListener().onReload();
      }
    });
  }
}