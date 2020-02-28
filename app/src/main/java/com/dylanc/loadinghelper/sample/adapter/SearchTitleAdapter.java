package com.dylanc.loadinghelper.sample.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.dylanc.loadinghelper.LoadingHelper;
import com.dylanc.loadinghelper.sample.R;
import com.dylanc.loadinghelper.sample.utils.KeyboardUtils;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import static com.wuhenzhizao.titlebar.widget.CommonTitleBar.*;

/**
 * @author Dylan Cai
 * @since 2019/6/25
 */
public class SearchTitleAdapter extends LoadingHelper.Adapter<SearchTitleAdapter.ViewHolder> {

  private OnSearchListener onSearchListener;

  public SearchTitleAdapter(OnSearchListener onSearchListener) {
    this.onSearchListener = onSearchListener;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
    View view = inflater.inflate(R.layout.loading_layout_search_title, parent, false);
    return new ViewHolder(view, onSearchListener);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder) {

  }

  static class ViewHolder extends LoadingHelper.ViewHolder {

    ViewHolder(@NonNull final View rootView, final OnSearchListener onSearchListener) {
      super(rootView);
      final CommonTitleBar titleBar = (CommonTitleBar) rootView;
      titleBar.setListener((v, action, extra) -> {
        EditText editText;
        switch (action) {
          case ACTION_LEFT_BUTTON:
            ((Activity) rootView.getContext()).finish();
            break;
          case ACTION_SEARCH_DELETE:
            editText = titleBar.getCenterSearchEditText();
            editText.setText("");
            break;
          case ACTION_SEARCH_SUBMIT:
            editText = titleBar.getCenterSearchEditText();
            String keyword = editText.getText().toString();
            KeyboardUtils.hideKeyboard(rootView.getContext(),editText);
            if (onSearchListener != null) {
              onSearchListener.onSearch(keyword);
            }
            break;
        }
      });
    }
  }

  public interface OnSearchListener {
    void onSearch(String keyword);
  }
}
