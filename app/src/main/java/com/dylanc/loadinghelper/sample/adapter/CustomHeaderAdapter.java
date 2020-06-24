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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.dylanc.loadinghelper.LoadingHelper;
import com.dylanc.loadinghelper.sample.R;

import org.jetbrains.annotations.NotNull;

/**
 * @author Dylan Cai
 */
public class CustomHeaderAdapter extends LoadingHelper.Adapter<CustomHeaderAdapter.ViewHolder> {

  private View.OnClickListener onMessageClickListener;
  private int firstDrawableId;
  private View.OnClickListener onFirstBtnClickListener;
  private int secondDrawableId;
  private View.OnClickListener onSecondBtnClickListener;

  public CustomHeaderAdapter(View.OnClickListener onMessageClickListener, int firstDrawableId,
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
    holder.btnMessage.setOnClickListener(onMessageClickListener);

    holder.btnFirst.setImageDrawable(ContextCompat.getDrawable(holder.getContext(),firstDrawableId));
    holder.btnFirst.setOnClickListener(onFirstBtnClickListener);

    holder.btnSecond.setImageDrawable(ContextCompat.getDrawable(holder.getContext(),secondDrawableId));
    holder.btnSecond.setOnClickListener(onSecondBtnClickListener);
  }

  static class ViewHolder extends LoadingHelper.ViewHolder {
    private final ImageView btnFirst;
    private final ImageView btnSecond;
    private final View btnMessage;

    ViewHolder(@NonNull View rootView) {
      super(rootView);
      btnFirst = rootView.findViewById(R.id.btn_first);
      btnSecond = rootView.findViewById(R.id.btn_second);
      btnMessage = rootView.findViewById(R.id.btn_message);
    }

    private Context getContext() {
      return getRootView().getContext();
    }
  }
}
