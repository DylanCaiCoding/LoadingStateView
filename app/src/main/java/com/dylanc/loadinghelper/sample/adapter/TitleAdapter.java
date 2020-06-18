package com.dylanc.loadinghelper.sample.adapter;

import android.app.Activity;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.dylanc.loadinghelper.LoadingHelper;
import com.dylanc.loadinghelper.sample.R;
import com.dylanc.loadinghelper.sample.base.BaseTitleAdapter;
import com.dylanc.loadinghelper.sample.base.NavIconType;
import com.dylanc.loadinghelper.sample.base.TitleConfig;

import org.jetbrains.annotations.NotNull;

/**
 * @author Dylan Cai
 * @since 2020/3/4
 */
public class TitleAdapter extends BaseTitleAdapter<TitleConfig, TitleAdapter.ViewHolder> {

  public TitleAdapter() {
  }

  public TitleAdapter(String title, NavIconType type) {
    setConfig(new TitleConfig(title, type));
  }

  @NotNull
  @Override
  public ViewHolder onCreateViewHolder(@NotNull LayoutInflater inflater, @NotNull ViewGroup parent) {
    return new TitleAdapter.ViewHolder(inflater.inflate(R.layout.layout_toolbar, parent, false));
  }

  @Override
  public void onBindViewHolder(@NotNull ViewHolder holder) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      holder.getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    if (!TextUtils.isEmpty(getConfig().getTitleText())) {
      holder.toolbar.setTitle(getConfig().getTitleText());
    }

    if (getConfig().getType() == NavIconType.BACK) {
      holder.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black);
      holder.toolbar.setNavigationOnClickListener(v -> holder.getActivity().finish());
    } else {
      holder.toolbar.setNavigationIcon(null);
    }

    if (getConfig().getMenuId() > 0 && getConfig().getOnMenuItemClickListener() != null) {
      holder.toolbar.inflateMenu(getConfig().getMenuId());
      holder.toolbar.setOnMenuItemClickListener(item -> getConfig().getOnMenuItemClickListener().invoke(item));
    }
  }

  static class ViewHolder extends LoadingHelper.ViewHolder {

    private final Toolbar toolbar;

    ViewHolder(@NonNull View rootView) {
      super(rootView);
      toolbar = (Toolbar) rootView;
    }

    private Activity getActivity() {
      return (Activity) getRootView().getContext();
    }
  }
}
