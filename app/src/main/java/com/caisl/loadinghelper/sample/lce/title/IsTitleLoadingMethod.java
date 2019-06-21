package com.caisl.loadinghelper.sample.lce.title;

import com.caisl.loadinghelper.LoadingHelper;

import static java.util.Objects.requireNonNull;

/**
 * @author caisl
 * @since 2019/6/20
 */
public class IsTitleLoadingMethod implements LoadingHelper.Method<Boolean, TitleAdapter.TitleViewHolder> {
  @Override
  public Boolean execute(TitleAdapter.TitleViewHolder holder, Object... params) {
    return null;
  }
}
