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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.dylanc.loadingstateview.LoadingStateView;
import com.dylanc.loadingstateview.ViewType;
import com.dylanc.loadingstateview.sample.java.R;
import com.dylanc.loadingstateview.sample.java.utils.KeyboardUtils;

/**
 * @author Dylan Cai
 */
public class SearchHeaderViewDelegate extends LoadingStateView.ViewDelegate<LoadingStateView.ViewHolder> {

  public static final String VIEW_TYPE_SEARCH = "search";
  private final OnSearchListener onSearchListener;

  public SearchHeaderViewDelegate(OnSearchListener onSearchListener) {
    super(VIEW_TYPE_SEARCH);
    this.onSearchListener = onSearchListener;
  }

  @NonNull
  @Override
  public LoadingStateView.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
    View view = inflater.inflate(R.layout.layout_search_header, parent, false);
    EditText edtSearch = view.findViewById(R.id.edt_search);
    edtSearch.setOnEditorActionListener((v, actionId, event) -> {
      if (actionId == EditorInfo.IME_ACTION_SEARCH) {
        KeyboardUtils.hideKeyboard(edtSearch);
        if (onSearchListener != null) {
          onSearchListener.onSearch(edtSearch.getText().toString());
        }
        return true;
      }
      return false;
    });
    return new LoadingStateView.ViewHolder(view);
  }

  public interface OnSearchListener {
    void onSearch(String keyword);
  }
}
