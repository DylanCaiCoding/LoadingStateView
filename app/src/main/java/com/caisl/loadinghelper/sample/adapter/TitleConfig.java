package com.caisl.loadinghelper.sample.adapter;


import android.view.View;

/**
 * @author caisl
 * @since 2019/4/9
 */
public class TitleConfig {

  private String mTitleText;
  private Type mType;
  private String mRightText;
  private View.OnClickListener mRightClickListener;

  public TitleConfig() {
  }

  public TitleConfig(String titleText, Type type) {
    mTitleText = titleText;
    mType = type;
  }

  public TitleConfig(String rightText, View.OnClickListener rightClickListener) {
    mRightText = rightText;
    mRightClickListener = rightClickListener;
  }

  public String getTitleText() {
    return mTitleText;
  }

  public void setTitleText(String titleText) {
    mTitleText = titleText;
  }

  public Type getType() {
    return mType;
  }

  public void setType(Type type) {
    mType = type;
  }

  public String getRightText() {
    return mRightText;
  }

  public void setRightText(String rightText) {
    mRightText = rightText;
  }

  public View.OnClickListener getRightClickListener() {
    return mRightClickListener;
  }

  public void setRightClickListener(View.OnClickListener rightClickListener) {
    mRightClickListener = rightClickListener;
  }

  public enum Type {
    BACK, NO_BACK
  }

}
