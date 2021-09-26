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

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.dylanc.loadingstateview.LoadingStateView;
import com.dylanc.loadingstateview.sample.R;

import org.jetbrains.annotations.NotNull;

/**
 * @author Dylan Cai
 */
public class CustomHeaderViewDelegate extends LoadingStateView.ViewDelegate<CustomHeaderViewDelegate.ViewHolder> {

  private final View.OnClickListener onMessageClickListener;
  private final int firstDrawableId;
  private final View.OnClickListener onFirstBtnClickListener;
  private final int secondDrawableId;
  private final View.OnClickListener onSecondBtnClickListener;

  public CustomHeaderViewDelegate(View.OnClickListener onMessageClickListener, int firstDrawableId,
                                  View.OnClickListener onFirstBtnClickListener, int secondDrawableId,
                                  View.OnClickListener onSecondBtnClickListener) {
    this.onMessageClickListener = onMessageClickListener;
    this.firstDrawableId = firstDrawableId;
    this.onFirstBtnClickListener = onFirstBtnClickListener;
    this.secondDrawableId = secondDrawableId;
    this.onSecondBtnClickListener = onSecondBtnClickListener;
  }

  @NotNull
  @Override
  public ViewHolder onCreateViewHolder(@NotNull LayoutInflater inflater, @NotNull ViewGroup parent) {
    return new ViewHolder(inflater.inflate(R.layout.layout_custom_header, parent, false));
  }

  @Override
  public void onBindViewHolder(@NotNull ViewHolder holder) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      holder.getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    holder.btnMessage.setOnClickListener(onMessageClickListener);

    holder.btnFirst.setImageDrawable(ContextCompat.getDrawable(holder.getActivity(), firstDrawableId));
    holder.btnFirst.setOnClickListener(onFirstBtnClickListener);

    holder.btnSecond.setImageDrawable(ContextCompat.getDrawable(holder.getActivity(), secondDrawableId));
    holder.btnSecond.setOnClickListener(onSecondBtnClickListener);
  }

  static class ViewHolder extends LoadingStateView.ViewHolder {
    private final ImageView btnFirst;
    private final ImageView btnSecond;
    private final View btnMessage;

    ViewHolder(@NonNull View rootView) {
      super(rootView);
      btnFirst = rootView.findViewById(R.id.btn_first);
      btnSecond = rootView.findViewById(R.id.btn_second);
      btnMessage = rootView.findViewById(R.id.btn_message);
    }

    private Activity getActivity() {
      return (Activity) getRootView().getContext();
    }
  }
}
