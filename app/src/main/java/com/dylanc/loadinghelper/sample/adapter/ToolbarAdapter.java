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

import android.app.Activity;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.dylanc.loadinghelper.LoadingHelper;
import com.dylanc.loadinghelper.sample.R;
import com.dylanc.loadinghelper.sample.base.BaseToolbarAdapter;
import com.dylanc.loadinghelper.sample.base.NavIconType;
import com.dylanc.loadinghelper.sample.base.ToolbarConfig;

import org.jetbrains.annotations.NotNull;

/**
 * @author Dylan Cai
 */
public class ToolbarAdapter extends BaseToolbarAdapter<ToolbarConfig, ToolbarAdapter.ViewHolder> {

  public ToolbarAdapter() {
  }

  public ToolbarAdapter(String title, NavIconType type) {
    setConfig(new ToolbarConfig(title, type));
  }

  @NotNull
  @Override
  public ViewHolder onCreateViewHolder(@NotNull LayoutInflater inflater, @NotNull ViewGroup parent) {
    return new ToolbarAdapter.ViewHolder(inflater.inflate(R.layout.layout_toolbar, parent, false));
  }

  @Override
  public void onBindViewHolder(@NotNull ViewHolder holder) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      holder.getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    if (!TextUtils.isEmpty(getConfig().getTitleText())) {
      holder.toolbar.setTitle(getConfig().getTitleText());
    }

    if (getConfig().getType() == NavIconType.BACK) {
      holder.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black);
      holder.toolbar.setNavigationOnClickListener(v -> holder.getActivity().finish());
    } else {
      holder.toolbar.setNavigationIcon(null);
    }

    if (getConfig().getMenuId() > 0 && getConfig().getOnMenuItemClick() != null) {
      holder.toolbar.inflateMenu(getConfig().getMenuId());
      holder.toolbar.setOnMenuItemClickListener(item -> getConfig().getOnMenuItemClick().invoke(item));
    }
  }

  static class ViewHolder extends LoadingHelper.ViewHolder {

    private final Toolbar toolbar;

    ViewHolder(@NonNull View rootView) {
      super(rootView);
      toolbar = (Toolbar) rootView;
    }

    private Activity getActivity() {
      return (Activity) getRootView().getContext();
    }
  }
}
