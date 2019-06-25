package com.caisl.loadinghelper.sample.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.caisl.loadinghelper.LoadingHelper;
import com.caisl.loadinghelper.sample.R;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import static com.wuhenzhizao.titlebar.widget.CommonTitleBar.*;

/**
 * @author caisl
 * @since 2019/6/25
 */
public class SearchTitleAdapter extends LoadingHelper.Adapter<SearchTitleAdapter.SearchTitleViewHolder> {

  private OnSearchListener mOnSearchListener;

  public SearchTitleAdapter(OnSearchListener onSearchListener) {
    mOnSearchListener = onSearchListener;
  }

  @NonNull
  @Override
  public SearchTitleViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
    View view = inflater.inflate(R.layout.loading_layout_search_title, parent, false);
    return new SearchTitleViewHolder(view, mOnSearchListener);
  }

  @Override
  public void onBindViewHolder(@NonNull SearchTitleViewHolder holder) {

  }

  class SearchTitleViewHolder extends LoadingHelper.ViewHolder {

    SearchTitleViewHolder(@NonNull final View rootView, final OnSearchListener onSearchListener) {
      super(rootView);
      final CommonTitleBar titleBar = (CommonTitleBar) rootView;
      titleBar.setListener(new CommonTitleBar.OnTitleBarListener() {
        @Override
        public void onClicked(View v, int action, String extra) {
          switch (action) {
            case ACTION_LEFT_BUTTON:
              ((Activity) rootView.getContext()).finish();
              break;
            case ACTION_SEARCH_DELETE:
              titleBar.getCenterSearchEditText().setText("");
              break;
            case ACTION_SEARCH_SUBMIT:
              String keyword = titleBar.getCenterSearchEditText().getText().toString();
              if (onSearchListener != null) {
                onSearchListener.onSearch(keyword);
              }
              break;
          }
        }
      });
    }
  }

  public interface OnSearchListener {
    void onSearch(String keyword);
  }
}
