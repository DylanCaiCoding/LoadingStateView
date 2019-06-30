package com.dylanc.loadinghelper.sample.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dylanc.loadinghelper.LoadingHelper;
import com.dylanc.loadinghelper.sample.R;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

/**
 * @author Dylan Cai
 * @since 2019/5/18
 */
public class TitleAdapter extends LoadingHelper.Adapter<TitleAdapter.ViewHolder> {

  private TitleConfig mConfig;

  public TitleAdapter() {
    this(new TitleConfig());
  }

  /**
   * add title and back button
   * @param title title text
   * @param type back button type
   */
  public TitleAdapter(String title,TitleConfig.Type type) {
    this(new TitleConfig(title,type));
  }

  /**
   * add right button
   * @param rightText right button text
   * @param rightClickListener right button click listener
   */
  public TitleAdapter(String rightText, View.OnClickListener rightClickListener) {
    this(new TitleConfig(rightText,rightClickListener));
  }

  public TitleAdapter(TitleConfig config) {
    mConfig = config;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
    return new ViewHolder(inflater.inflate(R.layout.loading_layout_title,parent,false));
  }

  @Override
  public void onBindViewHolder(@NonNull final ViewHolder holder) {
    if (!TextUtils.isEmpty(mConfig.getTitleText())){
      holder.setTitleText(mConfig.getTitleText());
    }
    if (mConfig.getType() == TitleConfig.Type.BACK){
      holder.addBackBtn();
    }else {
      holder.hideBackBtn();
    }
    if (!TextUtils.isEmpty(mConfig.getRightText())&&mConfig.getRightClickListener()!=null){
      holder.addRightBtn(mConfig.getRightText(),mConfig.getRightClickListener());
    }
  }

  class ViewHolder extends LoadingHelper.ViewHolder {

    private final CommonTitleBar mTitleBar;

    ViewHolder(@NonNull View rootView) {
      super(rootView);
      mTitleBar = (CommonTitleBar) rootView;
      mTitleBar.setListener(new CommonTitleBar.OnTitleBarListener() {
        @Override
        public void onClicked(View v, int action, String extra) {

        }
      });
    }

    void addBackBtn() {
      mTitleBar.getLeftImageButton().setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          ((Activity)rootView.getContext()).finish();
        }
      });
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
