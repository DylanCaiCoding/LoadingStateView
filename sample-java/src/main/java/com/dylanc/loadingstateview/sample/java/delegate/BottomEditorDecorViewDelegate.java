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

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.dylanc.loadingstateview.LoadingStateView;
import com.dylanc.loadingstateview.sample.java.R;
import com.dylanc.loadingstateview.sample.java.utils.KeyboardUtils;

import org.jetbrains.annotations.NotNull;

/**
 * @author Dylan Cai
 */
public class BottomEditorDecorViewDelegate extends LoadingStateView.DecorViewDelegate {
  private final OnSendListener onSendListener;

  public BottomEditorDecorViewDelegate(OnSendListener onSendListener) {
    this.onSendListener = onSendListener;
  }

  @NotNull
  @Override
  @SuppressLint("InflateParams")
  public View onCreateDecorView(@NotNull LayoutInflater inflater) {
    View view = inflater.inflate(R.layout.layout_bottom_editor, null);
    EditText edtContent = view.findViewById(R.id.edt_content);
    view.findViewById(R.id.btn_send).setOnClickListener(v -> {
      if (onSendListener != null) {
        onSendListener.onSend(edtContent.getText().toString());
        edtContent.setText("");
        KeyboardUtils.hideKeyboard(edtContent);
      }
    });
    return view;
  }

  @NotNull
  @Override
  public ViewGroup getContentParent(@NotNull View decorView) {
    return decorView.findViewById(R.id.content_parent);
  }

  public interface OnSendListener {
    void onSend(String content);
  }
}
