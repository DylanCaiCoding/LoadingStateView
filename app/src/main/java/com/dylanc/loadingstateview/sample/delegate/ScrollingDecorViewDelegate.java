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

package com.dylanc.loadingstateview.sample.delegate;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;

import com.dylanc.loadingstateview.LoadingStateView;
import com.dylanc.loadingstateview.sample.R;

import org.jetbrains.annotations.NotNull;

/**
 * @author Dylan Cai
 */
public class ScrollingDecorViewDelegate extends LoadingStateView.DecorViewDelegate {
  private final String title;

  public ScrollingDecorViewDelegate(String title) {
    this.title = title;
  }

  @NotNull
  @Override
  @SuppressLint("InflateParams")
  public View onCreateDecorView(@NotNull LayoutInflater inflater) {
    View view = inflater.inflate(R.layout.layout_scrolling_toolbar, null);
    Activity activity = (Activity) inflater.getContext();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }
    Toolbar toolbar = view.findViewById(R.id.toolbar);
    toolbar.setTitle(title);
    toolbar.setNavigationOnClickListener(v -> activity.finish());
    return view;
  }

  @NotNull
  @Override
  public ViewGroup getContentParent(@NotNull View decorView) {
    return decorView.findViewById(R.id.content_parent);
  }

}
