package com.dylanc.loadinghelper.sample.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.dylanc.loadinghelper.LoadingHelper;
import com.dylanc.loadinghelper.sample.R;
import com.dylanc.loadinghelper.sample.utils.KeyboardUtils;

/**
 * @author Dylan Cai
 * @since 2019/6/25
 */
public class SearchHeaderAdapter extends LoadingHelper.Adapter<SearchHeaderAdapter.ViewHolder> {

  private OnSearchListener onSearchListener;

  public SearchHeaderAdapter(OnSearchListener onSearchListener) {
    this.onSearchListener = onSearchListener;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
    View view = inflater.inflate(R.layout.layout_search_header, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder) {
    holder.edtSearch.setOnEditorActionListener((v, actionId, event) -> {
      if (actionId == EditorInfo.IME_ACTION_SEARCH) {
        KeyboardUtils.hideKeyboard(holder.getRootView().getContext(), holder.edtSearch);
        if (onSearchListener != null) {
          onSearchListener.onSearch(holder.edtSearch.getText().toString());
        }
        return true;
      }
      return false;
    });
  }

  static class ViewHolder extends LoadingHelper.ViewHolder {

    final EditText edtSearch;

    ViewHolder(@NonNull final View rootView) {
      super(rootView);
      edtSearch = rootView.findViewById(R.id.edt_search);
    }
  }

  public interface OnSearchListener {
    void onSearch(String keyword);
  }
}
