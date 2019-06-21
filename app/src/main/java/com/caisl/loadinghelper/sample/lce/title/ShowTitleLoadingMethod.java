package com.caisl.loadinghelper.sample.lce.title;

import com.caisl.loadinghelper.LoadingHelper;
import com.caisl.loadinghelper.sample.lce.title.TitleAdapter;

import static java.util.Objects.requireNonNull;

/**
 * @author caisl
 * @since 2019/6/20
 */
public class ShowTitleLoadingMethod implements LoadingHelper.Method<Void, TitleAdapter.TitleViewHolder> {
  @Override
  public Void execute(TitleAdapter.TitleViewHolder holder, Object... params) {
    boolean show = (boolean) requireNonNull(params[0]);
    if (show) {

    }
    return null;
  }
}
