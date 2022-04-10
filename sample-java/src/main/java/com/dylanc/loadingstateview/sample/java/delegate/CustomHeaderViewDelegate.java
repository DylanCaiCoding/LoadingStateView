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

package com.dylanc.loadingstateview.sample.java.delegate;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.dylanc.loadingstateview.LoadingStateView;
import com.dylanc.loadingstateview.ViewType;
import com.dylanc.loadingstateview.sample.java.R;

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
    super(ViewType.TITLE);
    this.onMessageClickListener = onMessageClickListener;
    this.firstDrawableId = firstDrawableId;
    this.onFirstBtnClickListener = onFirstBtnClickListener;
    this.secondDrawableId = secondDrawableId;
    this.onSecondBtnClickListener = onSecondBtnClickListener;
  }

  @NotNull
  @Override
  public ViewHolder onCreateViewHolder(@NotNull LayoutInflater inflater, @NotNull ViewGroup parent) {
    ViewHolder holder = new ViewHolder(inflater.inflate(R.layout.layout_custom_header, parent, false));
    holder.bind(onMessageClickListener, firstDrawableId, onFirstBtnClickListener, secondDrawableId, onSecondBtnClickListener);
    return holder;
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

    public void bind(View.OnClickListener onMessageClickListener, int firstDrawableId,
                     View.OnClickListener onFirstBtnClickListener, int secondDrawableId,
                     View.OnClickListener onSecondBtnClickListener) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
      }

      btnMessage.setOnClickListener(onMessageClickListener);

      btnFirst.setImageDrawable(ContextCompat.getDrawable(getActivity(), firstDrawableId));
      btnFirst.setOnClickListener(onFirstBtnClickListener);

      btnSecond.setImageDrawable(ContextCompat.getDrawable(getActivity(), secondDrawableId));
      btnSecond.setOnClickListener(onSecondBtnClickListener);
    }

    private Activity getActivity() {
      return (Activity) getRootView().getContext();
    }
  }
}
