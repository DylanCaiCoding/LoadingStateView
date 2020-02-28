package com.dylanc.loadinghelper.sample.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dylanc.loadinghelper.LoadingHelper;
import com.dylanc.loadinghelper.sample.R;
import com.dylanc.loadinghelper.sample.base.BaseTitleAdapter;
import com.dylanc.loadinghelper.sample.base.TitleConfig;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

/**
 * @author Dylan Cai
 * @since 2019/5/18
 */
public class TitleAdapter extends BaseTitleAdapter<TitleConfig, TitleAdapter.ViewHolder> {
  public TitleAdapter(){}

  public TitleAdapter(String title, TitleConfig.Type type) {
    config = new TitleConfig(title, type);
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
    return new ViewHolder(inflater.inflate(R.layout.loading_layout_title, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull final ViewHolder holder) {
    if (!TextUtils.isEmpty(getConfig().getTitleText())) {
      holder.setTitleText(getConfig().getTitleText());
    }
    if (getConfig().getType() == TitleConfig.Type.BACK) {
      holder.addBackBtn();
    } else {
      holder.hideBackBtn();
    }
    if (!TextUtils.isEmpty(getConfig().getRightText()) && getConfig().getOnRightBtnClickListener() != null) {
      holder.addRightBtn(getConfig().getRightText(), getConfig().getOnRightBtnClickListener());
    }
  }

  static class ViewHolder extends LoadingHelper.ViewHolder {

    private final CommonTitleBar mTitleBar;

    ViewHolder(@NonNull View rootView) {
      super(rootView);
      mTitleBar = (CommonTitleBar) rootView;
    }

    void addBackBtn() {
      mTitleBar.getLeftImageButton().setOnClickListener(v -> ((Activity) getRootView().getContext()).finish());
    }

    void hideBackBtn() {
      mTitleBar.getLeftImageButton().setImageDrawable(null);
    }

    void setTitleText(String title) {
      mTitleBar.getCenterTextView().setText(title);
    }

    void addRightBtn(String rightText, final View.OnClickListener rightClickListener) {
      mTitleBar.getRightTextView().setText(rightText);
      mTitleBar.getRightTextView().setOnClickListener(rightClickListener);
    }
  }
}
